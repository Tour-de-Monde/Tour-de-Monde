package com.ll.tourdemonde.payment.checkReservation.controller;

import com.ll.tourdemonde.global.rq.Rq;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.payment.checkReservation.dto.CheckReservationReqDto;
import com.ll.tourdemonde.payment.checkReservation.entity.CheckReservation;
import com.ll.tourdemonde.payment.checkReservation.service.CheckReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CheckReservationController {
    private final CheckReservationService checkReservationService;
    private final Rq rq;

    // 예약하기 버튼 눌렀을 때 엔드포인트
    @PostMapping("/reservation/{reservationId}/check")
    @PreAuthorize("isAuthenticated()")
    public String checkReservation(@PathVariable Long reservationId, @Valid CheckReservationReqDto requestDto, Model model) {
        Member buyer = rq.getMember();

        CheckReservation checkReservation = checkReservationService.checkReservation(reservationId, buyer, requestDto);

        model.addAttribute("checkReservation", checkReservation);

        return "domain/payment/order/detail";
    }
}
