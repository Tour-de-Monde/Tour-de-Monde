package com.ll.tourdemonde.payment.order.controller;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.member.service.MemberService;
import com.ll.tourdemonde.payment.checkReservation.service.CheckReservationService;
import com.ll.tourdemonde.payment.order.service.OrderService;
import com.ll.tourdemonde.place.dto.PlaceDto;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.repository.PlaceRepository;
import com.ll.tourdemonde.place.service.PlaceService;
import com.ll.tourdemonde.reservation.dto.ReservationCreateForm;
import com.ll.tourdemonde.reservation.dto.ReservationOptionForm;
import com.ll.tourdemonde.reservation.entity.Reservation;
import com.ll.tourdemonde.reservation.entity.ReservationType;
import com.ll.tourdemonde.reservation.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private CheckReservationService checkReservationService;

    @BeforeAll
    public void setup() { // @BeforeAll은 static만 붙여야 되는 줄 알았는데 아니네 public 붙이니깐 의존성 주입이 되네
        log.info("프로젝트 기본 세팅");
        Member admin = memberService.createMember("admin", "1234", "admin@test.com", "adminName", null, null, "adminNick");
        Member member1 = memberService.createMember("user1", "1234", "test1@test.com", "user1Name", null, null, "user1Nick");
        Member member2 = memberService.createMember("user2", "1234", "test2@test.com", "user2Name", null, null, "user2Nick");

        // 장소 등록
        placeService.save(new PlaceDto("장소1", "33.1, 37.1"));
        placeService.save(new PlaceDto("장소2", "33.2, 37.2"));
        placeService.save(new PlaceDto("장소3", "33.3, 37.3"));
        placeService.save(new PlaceDto("장소4", "33.4, 37.4"));

        Place place1 = placeRepository.findById(1L).get();
        Place place2 = placeRepository.findById(2L).get();
        Place place3 = placeRepository.findById(3L).get();

        // 업체 등록
        Reservation company1 = reservationService.createNewReservation(place1, new ReservationCreateForm(admin.getUsername(), place1.getId(), ReservationType.RESTAURANT));
        Reservation company2 = reservationService.createNewReservation(place2, new ReservationCreateForm(admin.getUsername(), place2.getId(), ReservationType.ACCOMMODATE));
        Reservation company3 = reservationService.createNewReservation(place3, new ReservationCreateForm(admin.getUsername(), place3.getId(), ReservationType.LEISURE));

        // 업체 예약 등록
        Reservation company1ReservationOp1 = reservationService.createNewReservationOption(new ReservationOptionForm(company1.getId(), "2024-02-04", "2024-02-04", "11:00", 50_000L));
        Reservation company1ReservationOp2 = reservationService.createNewReservationOption(new ReservationOptionForm(company1.getId(), "2024-02-05", "2024-02-06", "11:00", 150_000L));
        Reservation company1ReservationOp3 = reservationService.createNewReservationOption(new ReservationOptionForm(company1.getId(), "2024-02-06", "2024-02-06", "11:00", 150_000L));

        Reservation company2ReservationOp1 = reservationService.createNewReservationOption(new ReservationOptionForm(company2.getId(), "2024-02-03", "2024-02-03", "11:00", 100_000L));
        Reservation company2ReservationOp2 = reservationService.createNewReservationOption(new ReservationOptionForm(company2.getId(), "2024-02-04", "2024-02-05", "11:00", 150_000L));
    }


    // 사용자 예약 등록 - Order, CheckReservation 저장
    @Test
    @DisplayName("사용자 예약 등록")
    @WithUserDetails("user1")
    void t1() throws Exception {
        // GIVEN
//        Order order1 = checkReservationService.checkReservation(company2ReservationOp1.getId(), member1);
//        Order order2 = checkReservationService.checkReservation(company1ReservationOp1.getId(), member2);
        int reservationOpId = 1;

        // WHEN
        try {
            ResultActions resultActions = mvc
                    .perform(
                            post("/reservation/" + reservationOpId + "/check")
                                    .with(csrf())
                    )
                    .andDo(print());
        } catch (Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }

        // THEN
        /*resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(CheckReservationController.class))
                .andExpect(handler().methodName("showDetail"));*/

        // 검증
    }
}