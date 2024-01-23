package com.ll.tourdemonde.reservation.reservation.controller;

import com.ll.tourdemonde.reservation.reservation.dto.ReservationCreateForm;
import com.ll.tourdemonde.reservation.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reserve")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("")
    public String reservateItem(){
//        reservationService.createNewReservation(place, reservationDto);
        return "/domain/reservation/reservation";
    }

    @GetMapping("/manage")
    public String manageReservation(){
        return "/domain/reservation/manageReservation";
    }

    @GetMapping("/create")
    public String createNewReservation(){
        return "/domain/reservation/createNewReservation";
    }

    @PostMapping("/create")
    public String createNewReservation(
            @Valid ReservationCreateForm form,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/domain/reservation/createNewReservation";
        }
        reservationService.createNewReservation(form);
        return "/domain/reservation/createNewReservationDetail";
    }

    @GetMapping("/create/detail")
    public String createNewReservationDetail(){
        return "/domain/reservation/createNewReservationDetail";
    }
}
