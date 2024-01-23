package com.ll.tourdemonde.reservation.reservation.controller;

import com.ll.tourdemonde.reservation.reservation.dto.ReservationCreateForm;
import com.ll.tourdemonde.reservation.reservation.dto.ReservationOptionForm;
import com.ll.tourdemonde.reservation.reservation.entity.Reservation;
import com.ll.tourdemonde.reservation.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return "/domain/reservation/reservationExample";
    }

    @GetMapping("/{id}")
    public String showReservationFromPlace(@PathVariable("id") Long id
    ){
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
        Reservation reservation = reservationService.createNewReservation(form);
        return "redirect:/reserve/create/%d/detail".formatted(reservation.getId());
    }

    @GetMapping("/create/{id}/detail")
    public String createNewReservationDetail(
            @PathVariable("id") Long id,
            Model model){
        Reservation reservation = reservationService.findById(id);
        model.addAttribute("reservation", reservation);
        return "/domain/reservation/createNewReservationDetail";
    }

    @PostMapping("/create/{id}/detail")
    public String createNewReservationDetail(
            @PathVariable("id") Long id,
            @Valid ReservationOptionForm form,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "redirect:/reserve";
        }
        reservationService.createNewReservationOption(form);
        return "redirect:/reserve";
    }
}
