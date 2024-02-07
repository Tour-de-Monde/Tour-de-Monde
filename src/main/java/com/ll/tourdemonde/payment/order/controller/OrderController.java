package com.ll.tourdemonde.payment.order.controller;

import com.ll.tourdemonde.global.app.AppConfig;
import com.ll.tourdemonde.global.exception.GlobalException;
import com.ll.tourdemonde.global.rq.Rq;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.payment.checkReservation.entity.CheckReservation;
import com.ll.tourdemonde.payment.checkReservation.service.CheckReservationService;
import com.ll.tourdemonde.payment.order.entity.Order;
import com.ll.tourdemonde.payment.order.service.OrderService;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.service.PlaceService;
import com.ll.tourdemonde.reservation.entity.ReservationOption;
import com.ll.tourdemonde.reservation.entity.ReservationType;
import com.ll.tourdemonde.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final Rq rq;
    private final OrderService orderService;
    private final CheckReservationService checkReservationService;
    private final ReservationService reservationService;
    private final PlaceService  placeService;

    // 주문 현황 페이지
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showDetail(@PathVariable long id, Model model) {
        // TODO id로 Order를 찾으면 안되고 CheckReservation을 찾아야 하네
        CheckReservation checkReservation = checkReservationService.findById(id).orElse(null);

        if (checkReservation == null) {
            throw new GlobalException("400-1", "존재하지 않는 예약입니다.");
        }

        Member member = rq.getMember();

        if (!orderService.memberCheckReservation(member, checkReservation)) {
            throw new GlobalException("403", "권한이 없습니다.");
        }

        model.addAttribute("checkReservation", checkReservation);

        return "domain/payment/order/detail";
    }

/*
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showDetail(@PathVariable long id, Model model) {
        // TODO id로 Order를 찾으면 안되고 CheckReservation을 찾아야 하네
        Order order = orderService.findById(id).orElse(null);

        if (order == null) {
            throw new GlobalException("400-1", "존재하지 않는 주문입니다.");
        }

        Member member = rq.getMember();

        if (!orderService.memberCanSee(member, order)) {
            throw new GlobalException("403", "권한이 없습니다.");
        }

        model.addAttribute("order", order);

        return "domain/payment/order/detail";
    }
*/

    // 성공, 실패 코드 토스페이먼츠에서 제공한 코드를 따라가자.
    @GetMapping("/success")
    @PreAuthorize("isAuthenticated()")
    public String showSuccess() {
        return "domain/payment/order/success";
    }

    @GetMapping("/fail")
    @PreAuthorize("isAuthenticated()")
    public String showFail(String failCode, String failMessage) {
        rq.setAttribute("code", failCode); // model.addAttribute()써도 되고 rq.setAttribute() 써도 된다.
        rq.setAttribute("message", failMessage);

        return "domain/payment/order/fail";
    }

    // 마지막으로 물어보는 엔드 포인트
    @PostMapping("/confirm")
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody) throws Exception {

        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;
        try {
            // 클라이언트에서 받은 JSON 요청 바디입니다.
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // 체크 - orderId 주문번호에 amount 가격이 맞는지 물어본다.
        orderService.checkCanPay(orderId, Long.parseLong(amount));

        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        // TODO: 개발자센터에 로그인해서 내 결제위젯 연동 키 > 시크릿 키를 입력하세요. 시크릿 키는 외부에 공개되면 안돼요.
        // @docs https://docs.tosspayments.com/reference/using-api/api-keys
        String apiKey = AppConfig.getTossPaymentsWidgetSecretKey();

        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        // @docs https://docs.tosspayments.com/reference/using-api/authorization#%EC%9D%B8%EC%A6%9D
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((apiKey + ":").getBytes("UTF-8"));
        String authorizations = "Basic " + new String(encodedBytes, 0, encodedBytes.length);

        // 결제 승인 API를 호출하세요.
        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
        // @docs https://docs.tosspayments.com/guides/payment-widget/integration#3-결제-승인하기
        // 정보를 모아서 실제로 토스API한테 전송
        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200 ? true : false;

        // 결제 승인이 완료
        if (isSuccess) {
            orderService.payByTossPayments(orderService.findByCode(orderId).get(), Long.parseLong(amount));
        } else {
            throw new RuntimeException("결제 승인 실패");
        }

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        // TODO: 결제 성공 및 실패 비즈니스 로직을 구현하세요.
        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        return ResponseEntity.status(code).body(jsonObject);
    }


    // 주문 현황 페이지 - 희영: 예약 기능 완성을 위한 임시 엔드포인트
    @GetMapping("/{placeId}/{reservationId}/{optionId}")
    @PreAuthorize("isAuthenticated()")
    public String showDetail(
            @PathVariable("placeId") long placeId,
            @PathVariable("reservationId") long reservationId,
            @PathVariable("optionId") long optionId,
            Model model) {
        // order생성 및 조회에 필요한 정보 불러오기
        Member buyer = rq.getMember(); // 사용자
        Place place = placeService.findById(placeId); //예약 장소
        ReservationType type = reservationService.findById(reservationId).getType();//예약 종류
        ReservationOption option = reservationService.findOptionById(reservationId,optionId); //예약 옵션

        // 새로운 order 생성
        Order order = orderService.createNewOrderOrFind(buyer, option);

        model.addAttribute("order", order);
        model.addAttribute("place", place);
        model.addAttribute("reservationType", type);
        return "domain/payment/order/detail";
    }

    @PostMapping("/{orderId}")
    @PreAuthorize("isAuthenticated()")
    public String completeReservation(
            @PathVariable("orderId") Long orderId,
            Model model) {
        // 결제를 하지않고(캐쉬 차감 x) 결재완료
        Order order = orderService.payByChash(orderId);

        model.addAttribute("order", order);
        return "domain/payment/order/complete";
    }

    @PostMapping("/cancle/{orderId}")
    @PreAuthorize("isAuthenticated()")
    public String cancleOrder(){
        return "현재 미구현";
    }
}
