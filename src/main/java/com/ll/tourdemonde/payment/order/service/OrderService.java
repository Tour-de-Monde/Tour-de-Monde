package com.ll.tourdemonde.payment.order.service;

import com.ll.tourdemonde.global.exception.GlobalException;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.payment.cash.service.CashService;
import com.ll.tourdemonde.payment.order.entity.Order;
import com.ll.tourdemonde.payment.order.repository.OrderRepository;
import com.ll.tourdemonde.payment.checkReservation.entity.CheckReservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final CashService cashService;

    public Optional<Order> findById(long id) {
        return orderRepository.findById(id);
    }

    // 주문 상세페이지는 구매자만 볼 수 있습니다.
    public boolean memberCanSee(Member member, Order order) {
        return order.getBuyer().equals(member);
    }

    // 주문 상세페이지는 구매자만 볼 수 있습니다.
    public boolean memberCheckReservation(Member member, CheckReservation checkReservation) {
        return checkReservation.getOrder().getBuyer().equals(member);
    }

    // 주문자의 결제캐시랑 클라이언트측에서 준 결제캐시랑 같은지 확인
    public void checkCanPay(String orderCode, long pgPayPrice) {
        // 주문 찾기
        Order order = findByCode(orderCode).orElse(null);

        if (order == null) {
            throw new GlobalException("400-1", "존재하지 않는 주문입니다.");
        }

        // 결제일, 취소일이 null이 아닌 경우 이미 주문 완료했다.
        if (!order.isPayable()) {
            throw new GlobalException("400-3", "이미 결제한 주문입니다.");
        }

        // 생각해보기 TODO 예약에 적힌 가격이랑 pgPayPrice 가격이랑 같은지 확인
        if (order.getCheckReservation().getReservationOption().getPrice() != pgPayPrice)
            throw new GlobalException("400-2", "예약하신 금액과 등록된 결제 금액이 일치하지 않습니다.");
    }

    public Optional<Order> findByCode(String code) { // code : 2024-01-29__4
        long id = Long.parseLong(code.split("__", 2)[1]);

        return findById(id);
    }

    @Transactional
    public void payByTossPayments(Order order, long pgPayPrice) {
        Member buyer = order.getBuyer();
        long price = order.getCheckReservation().getReservationOption().getPrice();

        // TODO payByTossPayments() 이어서 작업하기
        // 결제 완료
        order.setPaymentDone();
    }
}
