package com.ll.tourdemonde.reservation.controller;

import com.ll.tourdemonde.global.exception.GlobalException;
import com.ll.tourdemonde.global.rq.Rq;
import com.ll.tourdemonde.global.rsData.RsData;
import com.ll.tourdemonde.global.util.Ut;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.service.PlaceService;
import com.ll.tourdemonde.reservation.dto.ReservationCreateForm;
import com.ll.tourdemonde.reservation.dto.ReservationOptionForm;
import com.ll.tourdemonde.reservation.entity.Reservation;
import com.ll.tourdemonde.reservation.entity.ReservationOption;
import com.ll.tourdemonde.reservation.entity.ReservationType;
import com.ll.tourdemonde.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reserve")
public class ReservationController {

    private final ReservationService reservationService;
    private final PlaceService placeService;
    private final Rq rq;

    //장소별 예약내역 조회
    @GetMapping("/{placeId}")
    public String showReservationFromPlace(@PathVariable("placeId") Long placeId,
                                           @RequestParam(name = "page", defaultValue = "1") int page,
                                           @RequestParam(name = "startDate", required = false) String startDate,
                                           @RequestParam(name = "endDate", required = false) String endDate,
                                           Model model
    ) {
        Place place = placeService.findById(placeId);

        // startDate와 endDate 기본값 설정
        LocalDateTime startDateConverted;
        if (startDate == null || startDate.isBlank()) {
            startDateConverted = LocalDate.now().atTime(0, 0);
        } else {
            startDateConverted = Ut.stringToLocalDateTime(startDate);
        }
        LocalDateTime endDateConverted;
        if (endDate == null || endDate.isBlank()) {
            endDateConverted = LocalDate.now().atTime(0, 0);
        } else {
            endDateConverted = Ut.stringToLocalDateTime(endDate);
        }

        // page가 1부터 시작하여 숫자 조정
        page = page - 1;
        RsData<Page<ReservationOption>> pageRsData = reservationService.findByDates(page, placeId, startDateConverted, endDateConverted);

        Page<ReservationOption> options = pageRsData.getData();
        // 모델에 추가
        model.addAttribute("place", place);
        model.addAttribute("options", options);
        return "domain/reservation/reservation";
    }

    //관리자 페이지
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/manage/{placeId}")
    public String manageReservation(@PathVariable("placeId") Long placeId,
                                    Model model) {
        // Todo 장소에 대한 소유주인지 확인하는 기능 추가 필요 240201, 장소에 member추가 및 등록 기능 필요
        if (!rq.getMember().isAdmin()) { // 관리자인지 확인
            throw new GlobalException("F-NoAdmin", "관리자가 아닙니다.");
        }

        Place place = placeService.findById(placeId);
        RsData<List<Reservation>> listRsData = reservationService.findAllByPlace(place);

        model.addAttribute("place", place);
        model.addAttribute("reservationList", listRsData.getData());

        return "domain/reservation/manageReservation";
    }

    // 예약 생성 페이지 GET
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{placeId}/create")
    public String createNewReservation(@PathVariable("placeId") Long placeId,
                                       Model model) {
        if (!rq.getMember().isAdmin()) { // 관리자인지 확인
            throw new GlobalException("F-NoAdmin", "관리자가 아닙니다.");
        }

        // 장소 가져오기
        Place place = placeService.findById(placeId);

        // ReservationType의 enum과 value를 Map으로 변환
        Map<String, String> reservationTypes = ReservationType.getMapValues();

        model.addAttribute("place", place);
        // 타입선택을 위해 모델에 추가
        model.addAttribute("reservationTypes", reservationTypes);
        return "domain/reservation/createNewReservation";
    }

    // 예약 생성 페이지 POST
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{placeId}/create")
    public String createNewReservation(
            @PathVariable("placeId") Long placeId,
            @Valid ReservationCreateForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("잘못된 값을 입력했습니다.");
        }

        if (!rq.getMember().isAdmin()) { // 관리자인지 확인
            throw new GlobalException("F-NoAdmin", "관리자가 아닙니다.");
        }

        try {
            Place place = placeService.findById(placeId);
            RsData<Reservation> rsData = reservationService.createNewReservation(place, form);

            if (form.getFlag().equals("continue")) {
                return rq.redirect("/reserve/create/%d/detail".formatted(rsData.getData().getId()), rsData.getMsg());
            } else {
                return rq.redirect("/reserve/manage/%d".formatted(placeId), rsData.getMsg());
            }
        } catch (Exception e) {
            return rq.redirect("/", e.getMessage());
        }
    }

    // 예약 옵션 생성페이지 GET
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create/{reservationId}/detail")
    public String createNewReservationOption(
            @PathVariable("reservationId") Long reservationId,
            Model model) {

        // 예약에 대한 권한 확인
        reservationService.checkAuthoritiesOfReservation(reservationId);

        Reservation reservation = reservationService.findById(reservationId);
        model.addAttribute("reservation", reservation);
        return "domain/reservation/createNewReservationOption";
    }

    // 예약 옵션 생성페이지 POST
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{reservationId}/detail")
    public String createNewReservationOption(
            @PathVariable("reservationId") Long reservationId,
            @Valid ReservationOptionForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("잘못된 값을 입력했습니다.");
        }
        try {
        // 예약에 대한 권한 확인
        reservationService.checkAuthoritiesOfReservation(reservationId);

        RsData<Reservation> reservationRsData = reservationService.createNewReservationOption(reservationId, form);

            return rq.redirect("/reserve/manage/%d".formatted(reservationRsData.getData().getPlace().getId()), reservationRsData.getMsg());
        } catch (Exception e) {
            return rq.redirect("/", e.getMessage());
        }
    }

    // 예약 수정페이지 GET
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{reservationId}")
    public String modifyReservation(@PathVariable("reservationId") Long reservationId,
                                    Model model) {
//권한 확인
        reservationService.checkAuthoritiesOfReservation(reservationId);

        Reservation reservation = reservationService.findById(reservationId);
        Map<String, String> reservationTypes = ReservationType.getMapValues();

        model.addAttribute("reservation", reservation);
        model.addAttribute("reservationTypes", reservationTypes);
        return "domain/reservation/modifyReservation";

    }

    // 예약 수정페이지 PUT
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/modify/{reservationId}")
    public String modifyReservation(@PathVariable("reservationId") Long reservationId,
                                    @Valid ReservationCreateForm form,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("잘못된 값을 입력했습니다.");
        }
        try {
        //권한 확인
        reservationService.checkAuthoritiesOfReservation(reservationId);

            Reservation reservation = reservationService.findById(reservationId);
            Long placeId = reservation.getPlace().getId();

            RsData<Reservation> reservationRsData = reservationService.modifyReservation(reservation, form);

            // 관리페이지로 이동
            return rq.redirect("/reserve/manage/%d".formatted(placeId), reservationRsData.getMsg());
        } catch (Exception e) {
            return rq.redirect("/", e.getMessage());
        }
    }

    // 예약 옵션 수정페이지 GET
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{reservationId}/detail/{optionId}")
    public String modifyOption(Model model,
                               @PathVariable("reservationId") Long reservationId,
                               @PathVariable("optionId") Long optionId) {

        //권한 확인
        reservationService.checkAuthoritiesOfReservation(reservationId);

        ReservationOption option = reservationService.findOptionById(reservationId, optionId);
        Reservation reservation = reservationService.findById(option.getReservation().getId());

        // ReservationType의 enum과 value를 Map으로 변환
        Map<String, String> reservationTypes = ReservationType.getMapValues();

        model.addAttribute("reservation", reservation);
        model.addAttribute("reservationTypes", reservationTypes);
        model.addAttribute("option", option);
        return "domain/reservation/modifyReservationOption";
    }

    // 예약 옵션 수정페이지 PUT
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/modify/{reservationId}/detail/{optionId}")
    public String modifyOption(
            @PathVariable("reservationId") Long reservationId,
            @PathVariable("optionId") Long optionId,
            @Valid ReservationOptionForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("잘못된 값을 입력했습니다.");
        }

        try {
            //권한 확인
            reservationService.checkAuthoritiesOfReservation(reservationId);

            ReservationOption option = reservationService.modifyReservationOption(reservationId, optionId, form);

            // move ManagePage
            Reservation reservation = reservationService.findById(reservationId);
            Long placeId = reservation.getPlace().getId();

            // 관리페이지로 이동
            return "redirect:/reserve/manage/%d".formatted(placeId);
        } catch (Exception e) {
            return rq.redirect("/", e.getMessage());
        }
    }

    // 예약 DELETE
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{reservationId}")
    public String deleteReservation(@PathVariable("reservationId") Long reservationId) {
        // move ManagePage
        Reservation reservation = reservationService.findById(reservationId);
        Long placeId = reservation.getPlace().getId();

        try {
            //권한 확인
            reservationService.checkAuthoritiesOfReservation(reservationId);

            // 예약 삭제
            reservationService.deleteReservation(reservationId);

            // 관리페이지로 이동
            return rq.redirect("/reserve/manage/%d".formatted(placeId), "삭제 성공");
        } catch (Exception e) {
            return rq.redirect("/reserve/manage/%d".formatted(placeId), "삭제 실패");
        }
    }

    // 예약 옵션 DELETE
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{reservationId}/detail/{optionId}")
    public String deleteReservationOption(@PathVariable("reservationId") Long reservationId,
                                          @PathVariable("optionId") Long optionId) {
        // move ManagePage
        Reservation reservation = reservationService.findById(reservationId);
        Long placeId = reservation.getPlace().getId();

        try {
            //권한 확인
            reservationService.checkAuthoritiesOfReservation(reservationId);

            // 예약 옵션 삭제
            reservationService.deleteOption(reservation, optionId);

            // 관리페이지로 이동
            return rq.redirect("/reserve/manage/%d".formatted(placeId), "성공");
        } catch (Exception e) {
            return rq.redirect("/reserve/manage/%d".formatted(placeId), "실패");
        }
    }

    @GetMapping("/list")
    public String showPlacesList(Model model){
        RsData<List<Place>> listRsData = placeService.findAllByOrderByNameAscCreateDateAsc();
        model.addAttribute("places", listRsData.getData());
        return "domain/reservation/placeList";
    }
}
