package com.ll.tourdemonde.payment.checkReservation.controller;

import com.ll.tourdemonde.global.rq.Rq;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.payment.checkReservation.service.CheckReservationService;
import com.ll.tourdemonde.payment.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CheckReservationController {
    private final CheckReservationService checkReservationService;
    private final Rq rq;

    // 예약하기 버튼 눌렀을 때 엔드포인트
    @PostMapping("/reservation/{reservationOpId}/check")
    @PreAuthorize("isAuthenticated()")
    public String checkReservation(@PathVariable("reservationOpId") Long reservationOpId) {
        Member buyer = rq.getMember();

        Order order = checkReservationService.checkReservation(reservationOpId, buyer);

        return rq.redirect("/order/" + order.getId(), "");
    }
}
