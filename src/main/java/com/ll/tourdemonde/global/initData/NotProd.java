package com.ll.tourdemonde.global.initData;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.member.service.MemberService;
import com.ll.tourdemonde.payment.checkReservation.service.CheckReservationService;
import com.ll.tourdemonde.payment.order.dto.OrderReservationReqDto;
import com.ll.tourdemonde.payment.order.entity.Order;
import com.ll.tourdemonde.payment.order.service.OrderService;
import com.ll.tourdemonde.place.dto.PlaceDto;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.repository.PlaceRepository;
import com.ll.tourdemonde.place.service.PlaceService;
import com.ll.tourdemonde.reservation.dto.ReservationCreateForm;
import com.ll.tourdemonde.reservation.dto.ReservationOptionForm;
import com.ll.tourdemonde.reservation.entity.Reservation;
import com.ll.tourdemonde.reservation.entity.ReservationOption;
import com.ll.tourdemonde.reservation.entity.ReservationType;
import com.ll.tourdemonde.reservation.repository.ReservationOptionRepository;
import com.ll.tourdemonde.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
@Profile("dev")
@Configuration
@RequiredArgsConstructor
public class NotProd {
    @Autowired
    @Lazy
    private NotProd self;
    private final MemberService memberService;
    private final PlaceService placeService;
    private final OrderService orderService;
    private final ReservationService reservationService;
    private final CheckReservationService checkReservationService;
    private final PlaceRepository placeRepository;
    private final ReservationOptionRepository reservationOptionRepository;

    @Bean
    ApplicationRunner initNotProd() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        if (memberService.findByUsername("admin").isPresent()) return;

        // 멤버 등록
        Member admin = memberService.createMember("admin", "1234", "admin@test.com", "adminName", null, null, "adminNick");
        Member member1 = memberService.createMember("user1", "1234", "test1@test.com", "박보검", null, null, "user1Nick");
        Member member2 = memberService.createMember("user2", "1234", "test2@test.com", "서강준", null, null, "user2Nick");

        // 장소 등록
        placeService.save(new PlaceDto("장소1", "서울특별시", 33.1, 37.1));
        placeService.save(new PlaceDto("장소2", "서울특별시", 33.2, 37.2));
        placeService.save(new PlaceDto("장소3", "서울특별시", 33.3, 37.3));
        placeService.save(new PlaceDto("장소4", "서울특별시", 33.4, 37.4));

        Place place1 = placeRepository.findById(1L).get();
        Place place2 = placeRepository.findById(2L).get();
        Place place3 = placeRepository.findById(3L).get();
        Place place4 = placeRepository.findById(4L).get();

        // 업체 등록
        Reservation company1 = reservationService.createNewReservation(place1, new ReservationCreateForm(admin.getUsername(), place1.getId(), ReservationType.RESTAURANT, "continue")).getData();
        Reservation company2 = reservationService.createNewReservation(place2, new ReservationCreateForm(admin.getUsername(), place2.getId(), ReservationType.ACCOMMODATE,"continue")).getData();
        Reservation company3 = reservationService.createNewReservation(place3, new ReservationCreateForm(admin.getUsername(), place3.getId(), ReservationType.LEISURE,"continue")).getData();

        // 업체 예약 등록
        reservationService.createNewReservationOption(company1.getId(), new ReservationOptionForm(company1.getId(), "2024-02-04", "2024-02-04", "11:00", 50_000L, 3_000L));
        reservationService.createNewReservationOption(company1.getId(), new ReservationOptionForm(company1.getId(), "2024-02-05", "2024-02-06", "11:00", 60_000L,3_000L));
        reservationService.createNewReservationOption(company1.getId(), new ReservationOptionForm(company1.getId(), "2024-02-06", "2024-02-06", "11:00", 70_000L,3_000L));

        reservationService.createNewReservationOption(company2.getId(), new ReservationOptionForm(company2.getId(), "2024-02-03", "2024-02-03", "11:00", 80_000L,3_000L));
        reservationService.createNewReservationOption(company2.getId(), new ReservationOptionForm(company2.getId(), "2024-02-04", "2024-02-05", "11:00", 90_000L,3_000L));

        ReservationOption reservationOption1 = reservationOptionRepository.findById(1L).get();
        ReservationOption reservationOption2 = reservationOptionRepository.findById(2L).get();
        ReservationOption reservationOption3 = reservationOptionRepository.findById(3L).get();

        ReservationOption reservationOption4 = reservationOptionRepository.findById(4L).get();
        ReservationOption reservationOption5 = reservationOptionRepository.findById(5L).get();

        OrderReservationReqDto dto = new OrderReservationReqDto();
        dto.setAdultCount(1L);
        dto.setChildrenCount(1L);
        // 사용자 예약 등록 - Order, CheckReservation 저장
        Order order1 = checkReservationService.checkReservation(reservationOption1.getId(), member1, dto);
        Order order2 = checkReservationService.checkReservation(reservationOption4.getId(), member2, dto);

        // 토스페이먼츠 결제
//        orderService.payByTossPayments(order1, 100_000L);
        orderService.payByTossPayments(order2, 50_000L);
    }
}