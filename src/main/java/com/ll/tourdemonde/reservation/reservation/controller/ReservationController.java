package com.ll.tourdemonde.reservation.reservation.controller;

import com.ll.tourdemonde.global.rsData.RsData;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.service.PlaceService;
import com.ll.tourdemonde.reservation.reservation.dto.ReservationCreateForm;
import com.ll.tourdemonde.reservation.reservation.dto.ReservationOptionForm;
import com.ll.tourdemonde.reservation.reservation.entity.Reservation;
import com.ll.tourdemonde.reservation.reservation.entity.ReservationOption;
import com.ll.tourdemonde.reservation.reservation.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
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
    public String reservateItem() {
//        reservationService.createNewReservation(place, reservationDto);
        return "/domain/reservation/reservationExample";
    }

    @GetMapping("/{placeId}")
    public String showReservationFromPlace(@PathVariable("placeId") Long placeId,
                                           @RequestParam(name = "startDate", required = false) String startDate,
                                           @RequestParam(name = "endDate", required = false) String endDate,
                                           Model model
    ) {
        //ToDo 순환참조의 위험이 있으니 차후 가져오는 데이터를 개선하도록 한다.
        // ToDo 차후 place id로 검색을 하여 place에 있는 예약, 예약 옵션 다 보여주기
        Place place = placeService.findById(placeId);
        Reservation reservation = reservationService.findById(placeId);
        model.addAttribute("place",place);
        model.addAttribute("reservation", reservation);
        return "/domain/reservation/reservation";
    }

    @GetMapping("/create")
    public String createNewReservation() {
        return "/domain/reservation/createNewReservation";
    }

    @PostMapping("/create")
    public String createNewReservation(
            @Valid ReservationCreateForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/domain/reservation/createNewReservation";
        }
        Reservation reservation = reservationService.createNewReservation(form);
        return "redirect:/reserve/create/%d/detail".formatted(reservation.getId());
    }

    @GetMapping("/create/{reservationId}/detail")
    public String createNewReservationOption(
            @PathVariable("reservationId") Long id,
            Model model) {
        Reservation reservation = reservationService.findById(id);
        model.addAttribute("reservation", reservation);
        return "/domain/reservation/createNewReservationOption";
    }

    @PostMapping("/create/{reservationId}/detail")
    public String createNewReservationOption(
            @PathVariable("reservationId") Long id,
            @Valid ReservationOptionForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/reserve";
        }
        reservationService.createNewReservationOption(form);
        return "redirect:/reserve";
    }

    //관리자 페이지
    @GetMapping("/manage/{placeId}")
    public String manageReservation(@PathVariable("placeId") long placeId,
                                    Model model) {
        // ToDo 차후 place id로 검색을 하여 place에 있는 예약, 예약 옵션 다 보여주기
        RsData<Place> placeRsData = placeService.findByPlace(placeId);
        RsData<List<Reservation>> listRsData = reservationService.findAllByPlace(placeRsData.getData());

        model.addAttribute("place", placeRsData.getData());
        model.addAttribute("reservationList", listRsData.getData());

        if (listRsData.isFail()) {
            // ToDo 차후 메세지와 함께 페이지 이동하도록 수정
            return "/domain/reservation/manageReservation";
        }

        return "/domain/reservation/manageReservation";
    }

    @GetMapping("/modify/{reservationId}")
    public String modifyReservation(@PathVariable("reservationId") Long id,
                                    Model model) {
        Reservation reservation = reservationService.findById(id);

        model.addAttribute("reservation", reservation);
        return "/domain/reservation/modifyReservation";
    }

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
            String preURL = request.getHeader("Referer");
            return "redirect:" + preURL;
        }

        // 관리페이지로 이동
        return "redirect:/reserve/manage/%d".formatted(placeId);
    }

    @GetMapping("/modify/{reservationId}/detail/{optionId}")
    public String modifyOption(Model model,
                               @PathVariable("reservationId") Long reservationId,
                               @PathVariable("optionId") Long optionId) {

        ReservationOption option = reservationService.findOptionById(reservationId, optionId);
        Reservation reservation = reservationService.findById(option.getReservation().getId());

        model.addAttribute("reservation", reservation);
        model.addAttribute("option", option);
        return "/domain/reservation/modifyReservationOption";
    }

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

    @DeleteMapping("/delete/{reservationId}/{optionId}")
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
