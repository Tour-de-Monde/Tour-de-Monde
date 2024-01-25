package com.ll.tourdemonde.reservation.reservation.controller;

import com.ll.tourdemonde.global.rsData.RsData;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.service.PlaceService;
import com.ll.tourdemonde.reservation.reservation.dto.ReservationCreateForm;
import com.ll.tourdemonde.reservation.reservation.dto.ReservationOptionForm;
import com.ll.tourdemonde.reservation.reservation.entity.Reservation;
import com.ll.tourdemonde.reservation.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reserve")
public class ReservationController {

    private final ReservationService reservationService;
    private final PlaceService placeService;

    @GetMapping("")
    public String reservateItem(){
//        reservationService.createNewReservation(place, reservationDto);
        return "/domain/reservation/reservationExample";
    }

    @GetMapping("/{reservationId}")
    public String showReservationFromPlace(@PathVariable("reservationId") Long id,
                                           @RequestParam(name = "startDate", required = false) String startDate,
                                           @RequestParam(name = "endDate", required = false) String endDate,
                                           Model model
    ){
        //ToDo 순환참조의 위험이 있으니 차후 가져오는 데이터를 개선하도록 한다.
        // ToDo 차후 place id로 검색을 하여 place에 있는 예약, 예약 옵션 다 보여주기
        Reservation reservation = reservationService.findById(id);
        model.addAttribute("reservation", reservation);
        return "/domain/reservation/reservation";
    }

    @GetMapping("/manage/{placeId}")
    public String manageReservation(@PathVariable("placeId") long id,
                                    Model model){
        // ToDo 차후 place id로 검색을 하여 place에 있는 예약, 예약 옵션 다 보여주기
        RsData<Place> placeRsData = placeService.findByPlace(id);
        RsData<List<Reservation>> listRsData = reservationService.findAllByPlace(placeRsData.getData());

        if (listRsData.isFail()){
            // ToDo 차후 메세지와 함께 페이지 이동하도록 수정
            return "/domain/reservation/manageReservation";
        }
        model.addAttribute("place", placeRsData.getData());
        model.addAttribute("reservationList", listRsData.getData());
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

    @GetMapping("/create/{reservationId}/detail")
    public String createNewReservationDetail(
            @PathVariable("reservationId") Long id,
            Model model){
        Reservation reservation = reservationService.findById(id);
        model.addAttribute("reservation", reservation);
        return "/domain/reservation/createNewReservationDetail";
    }

    @PostMapping("/create/{reservationId}/detail")
    public String createNewReservationDetail(
            @PathVariable("reservationId") Long id,
            @Valid ReservationOptionForm form,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "redirect:/reserve";
        }
        reservationService.createNewReservationOption(form);
        return "redirect:/reserve";
    }

    @GetMapping("/modify/{reservationId}")
    public String modifyReservation(@PathVariable("reservationId")Long id,
                                    Model model){
        Reservation reservation = reservationService.findById(id);

        model.addAttribute("reservation", reservation);
        return "/domain/reservation/modifyReservation";
    }

    @PostMapping("/modify/{reservationId}")
    public String modifyReservation(@PathVariable("reservationId")Long id,
                                    ReservationCreateForm form,
                                    BindingResult bindingResult,
                                    Model model){
        if(bindingResult.hasErrors()){
            throw new RuntimeException("잘못된 값을 입력했습니다.");
        }

        Reservation reservation = reservationService.findById(id);

        RsData<Reservation> reservationRsData = reservationService.modifyReservation(reservation, form);

        if(reservationRsData.isFail()){
            throw new RuntimeException();
        }

        return manageReservation(id, model);
    }
}
