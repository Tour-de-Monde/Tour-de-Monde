package com.ll.tourdemonde.reservation.controller;

import com.ll.tourdemonde.global.rsData.RsData;
import com.ll.tourdemonde.member.service.MemberService;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.service.PlaceService;
import com.ll.tourdemonde.reservation.dto.ReservationCreateForm;
import com.ll.tourdemonde.reservation.dto.ReservationOptionForm;
import com.ll.tourdemonde.reservation.entity.Reservation;
import com.ll.tourdemonde.reservation.entity.ReservationOption;
import com.ll.tourdemonde.reservation.entity.ReservationType;
import com.ll.tourdemonde.reservation.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reserve")
public class ReservationController {

    private final ReservationService reservationService;
    private final PlaceService placeService;
    private final MemberService memberService;

    // 샘플페이지 Todo 차후 삭제 예정
    @GetMapping("")
    public String reservateItem() {
        return "domain/reservation/reservationExample";
    }

    //장소별 예약내역 조회
    @GetMapping("/{placeId}")
    public String showReservationFromPlace(@PathVariable("placeId") Long placeId,
                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "startDate", required = false) String startDate,
                                           @RequestParam(name = "endDate", required = false) String endDate,
                                           Model model,
                                           HttpServletRequest request
    ) {
        //ToDo 순환참조의 위험이 있으니 차후 가져오는 데이터를 개선하도록 한다.
//        try {
        Place place = placeService.findById(placeId);
        RsData<List<Reservation>> listRsData = reservationService.findAllByPlace(place);
        List<Reservation> reservationList = listRsData.getData();
//
//        // startDate와 endDate 기본값 설정
//        LocalDateTime startDateConverted;
//        if (startDate.isBlank()) {
//            startDateConverted = LocalDateTime.now();
//        } else {
//            startDateConverted = Ut.stringToLocalDateTime(startDate);
//        }
//        LocalDateTime endDateConverted;
//        if (endDate.isBlank()) {
//            endDateConverted = LocalDateTime.now();
//        } else {
//            endDateConverted = Ut.stringToLocalDateTime(endDate);
//        }
//
//        RsData<Page<Reservation>> pageRsData = reservationService.findByDates(page, placeId, startDateConverted, endDateConverted);

        // 모델에 추가
        model.addAttribute("place", place);
        model.addAttribute("reservationList", reservationList);
//        model.addAttribute("pages", pageRsData.getData());
        return "domain/reservation/reservation";
//        } catch (Exception e) {
//            throw new RuntimeException();
//        }
    }

    //관리자 페이지
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/manage/{placeId}")
    public String manageReservation(@PathVariable("placeId") Long placeId,
                                    Model model,
                                    HttpServletRequest request) {
        String preUrl = request.getHeader("Referer");
        // Todo 장소에 대한 소유주인지 확인하는 기능 추가 필요 240201, 장소에 member추가 및 등록 기능 필요

        try {
            Place place = placeService.findById(placeId);
            RsData<List<Reservation>> listRsData = reservationService.findAllByPlace(place);

            model.addAttribute("place", place);
            model.addAttribute("reservationList", listRsData.getData());
        } catch (Exception e) {
            return "redirect:" + preUrl;
        }

        return "domain/reservation/manageReservation";
    }

    // 예약 생성 페이지 GET
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{placeId}/create")
    public String createNewReservation(@PathVariable("placeId") Long placeId,
                                       Model model) {
        try {
            // 장소 가져오기
            Place place = placeService.findById(placeId);

            // ReservationType의 enum과 value를 Map으로 변환
            Map<String, String> reservationTypes = ReservationType.getMapValues();

            model.addAttribute("place", place);
            // 타입선택을 위해 모델에 추가
            model.addAttribute("reservationTypes", reservationTypes);
            return "domain/reservation/createNewReservation";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    // 예약 생성 페이지 POST
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{placeId}/create")
    public String createNewReservation(
            @PathVariable("placeId") Long placeId,
            @Valid ReservationCreateForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "domain/reservation/createNewReservation";
        }
        try {
            Place place = placeService.findById(placeId);
            Reservation reservation = reservationService.createNewReservation(place, form);
            return "redirect:/reserve/create/%d/detail".formatted(reservation.getId());
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    // 예약 옵션 생성페이지 GET
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create/{reservationId}/detail")
    public String createNewReservationOption(
            @PathVariable("reservationId") Long reservationId,
            Model model) {
        Reservation reservation = reservationService.findById(reservationId);
        model.addAttribute("reservation", reservation);
        return "domain/reservation/createNewReservationOption";
    }

    // 예약 옵션 생성페이지 POST
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{reservationId}/detail")
    public String createNewReservationOption(
            @PathVariable("reservationId") Long id,
            @Valid ReservationOptionForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/reserve";
        }
        Reservation reservation = reservationService.createNewReservationOption(form);
        return "redirect:/reserve/manage/%d".formatted(reservation.getPlace().getId());
    }

    // 예약 수정페이지 GET
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{reservationId}")
    public String modifyReservation(@PathVariable("reservationId") Long id,
                                    Model model) {
        Reservation reservation = reservationService.findById(id);
        Map<String, String> reservationTypes = ReservationType.getMapValues();

        model.addAttribute("reservation", reservation);
        model.addAttribute("reservationTypes", reservationTypes);
        return "domain/reservation/modifyReservation";
    }

    // 예약 수정페이지 PUT
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/modify/{reservationId}")
    public String modifyReservation(@PathVariable("reservationId") Long reservationId,
                                    @Valid ReservationCreateForm form,
                                    BindingResult bindingResult,
                                    HttpServletRequest request,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("잘못된 값을 입력했습니다.");
        }

        Reservation reservation = reservationService.findById(reservationId);
        Long placeId = reservation.getPlace().getId();

        RsData<Reservation> reservationRsData = reservationService.modifyReservation(reservation, form);

        if (reservationRsData.isFail()) {
//            String preURL = request.getHeader("Referer");
            return "redirect:/reserve/modify/%d".formatted(reservationId);
        }

        // 관리페이지로 이동
        return "redirect:/reserve/manage/%d".formatted(placeId);
    }

    // 예약 옵션 수정페이지 GET
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{reservationId}/detail/{optionId}")
    public String modifyOption(Model model,
                               @PathVariable("reservationId") Long reservationId,
                               @PathVariable("optionId") Long optionId) {

        ReservationOption option = reservationService.findOptionById(reservationId, optionId);
        Reservation reservation = reservationService.findById(option.getReservation().getId());

        // ReservationType의 enum과 value를 Map으로 변환
        Map<String, String> reservationTypes = ReservationType.getMapValues();

        model.addAttribute("reservation", reservation);
        model.addAttribute("reservationTypes", reservationTypes);
        model.addAttribute("option", option);
        return "domain/reservation/modifyReservationOption";
    }

    // 예약 옵션 수정페이지 PUT
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/modify/{reservationId}/detail/{optionId}")
    public String modifyOption(
            @PathVariable("reservationId") Long reservationId,
            @PathVariable("optionId") Long optionId,
            @Valid ReservationOptionForm form,
            BindingResult bindingResult,
            HttpServletRequest request,
            Model model) {
        if (bindingResult.hasErrors()) {
            String preURL = request.getHeader("Referer");
            return "redirect:" + preURL;
        }
        ReservationOption option = reservationService.modifyReservationOption(reservationId, optionId, form);

        // move ManagePage
        Reservation reservation = reservationService.findById(reservationId);
        Long placeId = reservation.getPlace().getId();

        // 관리페이지로 이동
        return "redirect:/reserve/manage/%d".formatted(placeId);
    }

    // 예약 DELETE
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{reservationId}")
    public String deleteReservation(@PathVariable("reservationId") Long reservationId,
                                    Model model) {
        // move ManagePage
        Reservation reservation = reservationService.findById(reservationId);
        Long placeId = reservation.getPlace().getId();

        // 예약 삭제
        reservationService.deleteReservation(reservationId);

        // 관리페이지로 이동
        return "redirect:/reserve/manage/%d".formatted(placeId);
    }

    // 예약 옵션 DELETE
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{reservationId}/detail/{optionId}")
    public String deleteReservationOption(@PathVariable("reservationId") Long reservationId,
                                          @PathVariable("optionId") Long optionId,
                                          Model model) {
        // move ManagePage
        Reservation reservation = reservationService.findById(reservationId);
        Long placeId = reservation.getPlace().getId();

        // 예약 옵션 삭제
        reservationService.deleteOption(reservation, optionId);

        // 관리페이지로 이동
        return "redirect:/reserve/manage/%d".formatted(placeId);
    }
}
