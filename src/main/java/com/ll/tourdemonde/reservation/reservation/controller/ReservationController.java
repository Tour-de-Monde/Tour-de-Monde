package com.ll.tourdemonde.reservation.reservation.controller;

import com.ll.tourdemonde.reservation.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reserve")
public class ReservationController {

    private final ReservationService reservationService;

    @RequestMapping("")
    public String reservateItem(){
//        reservationService.createNewReservation(place, reservationDto);
        return "/domain/reservation/reservation";
    }

    @RequestMapping("/create")
    public String createNewReservation(){
        return "/domain/reservation/createNewReservation";
    }

    @RequestMapping("/create/detail")
    public String createNewReservationDetail(){
        return "/domain/reservation/createNewReservationDetail";
    }
}
