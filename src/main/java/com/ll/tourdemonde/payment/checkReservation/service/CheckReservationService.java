package com.ll.tourdemonde.payment.checkReservation.service;

import com.ll.tourdemonde.global.exception.GlobalException;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.payment.checkReservation.entity.CheckReservation;
import com.ll.tourdemonde.payment.checkReservation.repository.CheckReservationRepository;
import com.ll.tourdemonde.payment.order.entity.Order;
import com.ll.tourdemonde.payment.order.repository.OrderRepository;
import com.ll.tourdemonde.reservation.entity.ReservationOption;
import com.ll.tourdemonde.reservation.repository.ReservationOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckReservationService {
    private final CheckReservationRepository checkReservationRepository;
    private final ReservationOptionRepository reservationOptionRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Order checkReservation(Long reservationOpId, Member buyer) {
        // 예약 옵션 찾기
        ReservationOption reservationOption = reservationOptionRepository.findById(reservationOpId).orElse(null);

        if (reservationOption == null) {
            throw new GlobalException("400", "현재 장소에 등록된 예약이 없습니다.");
        }

        CheckReservation checkedReservation = saveCheckReservation(reservationOption);

        // 주문 저장
        Order order = Order.builder()
                .buyer(buyer)
                .checkReservation(checkedReservation)
                .build();

        Order ordered = orderRepository.save(order);

        checkedReservation.setOrder(ordered);

        return order;
    }

    @Transactional
    public CheckReservation saveCheckReservation(ReservationOption reservationOption) {
        // CheckReservation 저장
        CheckReservation reservationDetail = CheckReservation.builder()
                .order(null)
                .reservationOption(reservationOption)
                .build();

        CheckReservation checkedReservation = checkReservationRepository.save(reservationDetail);
        return checkedReservation;
    }

    public Optional<CheckReservation> findById(long id) {
        return checkReservationRepository.findById(id);
    }
}
