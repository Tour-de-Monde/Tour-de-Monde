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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping("")
    public String reservateItem() {
//        reservationService.createNewReservation(place, reservationDto);
        return "domain/reservation/reservationExample";
    }

    @GetMapping("/{placeId}")
    public String showReservationFromPlace(@PathVariable("placeId") Long placeId,
                                           @RequestParam(name = "startDate", required = false) String startDate,
                                           @RequestParam(name = "endDate", required = false) String endDate,
                                           Model model,
                                           HttpServletRequest request
    ) {
        //ToDo 순환참조의 위험이 있으니 차후 가져오는 데이터를 개선하도록 한다.
        try {
            Place place = placeService.findById(placeId);
            RsData<List<Reservation>> listRsData = reservationService.findAllByPlace(place);
            List<Reservation> reservationList = listRsData.getData();

            // 모델에 추가
            model.addAttribute("place", place);
            model.addAttribute("reservationList", reservationList);
            return "domain/reservation/reservation";
        } catch (Exception e) {
            String preUrl = request.getHeader("Referer");
            return "redirect:" + preUrl;
        }

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{placeId}/create")
    public String createNewReservation(@PathVariable("placeId") Long placeId,
                                       @AuthenticationPrincipal UserDetails userDetails,
                                       Model model) {

        try{
            // 장소 가져오기
            Place place = placeService.findById(placeId);
            //Todo 현재 유저 가져오기, 유저 정보 입력한 상태로 reservation 생성 240131
            String username = userDetails.getUsername();

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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{placeId}/create")
    public String createNewReservation(
            @PathVariable("placeId")Long placeId,
            @Valid ReservationCreateForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "domain/reservation/createNewReservation";
        }
        try {
            Place place = placeService.findById(placeId);
            Reservation reservation = reservationService.createNewReservation(place, form);
            return "redirect:/reserve/create/%d/detail".formatted(reservation.getId());
        } catch (Exception e){
            return "redirect:/";
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create/{reservationId}/detail")
    public String createNewReservationOption(
            @PathVariable("reservationId") Long reservationId,
            Model model) {
        // Todo 유저 정보가져오기
        Reservation reservation = reservationService.findById(reservationId);
        model.addAttribute("reservation", reservation);
        return "domain/reservation/createNewReservationOption";
    }

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

    //관리자 페이지
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/manage/{placeId}")
    public String manageReservation(@PathVariable("placeId") Long placeId,
                                    Model model,
                                    HttpServletRequest request) {
        // ToDo 차후 place id로 검색을 하여 place에 있는 예약, 예약 옵션 다 보여주기
        String preUrl = request.getHeader("Referer");
        try{
            Place place = placeService.findById(placeId);
            RsData<List<Reservation>> listRsData = reservationService.findAllByPlace(place);

            model.addAttribute("place", place);
            model.addAttribute("reservationList", listRsData.getData());
        } catch (Exception e){
            return "redirect:" + preUrl;
        }

        return "domain/reservation/manageReservation";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{reservationId}")
    public String modifyReservation(@PathVariable("reservationId") Long id,
                                    Model model) {
        Reservation reservation = reservationService.findById(id);

        model.addAttribute("reservation", reservation);
        return "domain/reservation/modifyReservation";
    }

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
            String preURL = request.getHeader("Referer");
            return "redirect:" + preURL;
        }

        // 관리페이지로 이동
        return "redirect:/reserve/manage/%d".formatted(placeId);
    }

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
