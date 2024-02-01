//package com.ll.tourdemonde.reservation.checkReservation.init;
//
//import com.ll.tourdemonde.member.entity.Member;
//import com.ll.tourdemonde.member.service.MemberService;
//import com.ll.tourdemonde.place.dto.PlaceDto;
//import com.ll.tourdemonde.place.entity.Place;
//import com.ll.tourdemonde.place.service.PlaceService;
//import com.ll.tourdemonde.reservation.checkReservation.dto.CheckReservationReqDto;
//import com.ll.tourdemonde.reservation.checkReservation.service.CheckReservationService;
//import com.ll.tourdemonde.reservation.dto.ReservationCreateForm;
//import com.ll.tourdemonde.reservation.entity.Reservation;
//import com.ll.tourdemonde.reservation.entity.ReservationType;
//import com.ll.tourdemonde.reservation.service.ReservationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.transaction.annotation.Transactional;
//
//@Configuration
//@RequiredArgsConstructor
//public class CheckReservationInit {
//    @Autowired
//    @Lazy
//    private CheckReservationInit self;
//    private final MemberService memberService;
//    private final PlaceService placeService;
//    private final ReservationService reservationService;
//    private final CheckReservationService checkReservationService;
//
//    @Bean
//    ApplicationRunner initNotProd() {
//        return args -> {
//            self.work1();
//        };
//    }
//
//    @Transactional
//    public void work1() {
//        if (memberService.findByUsername("admin").isPresent()) return;
//
//        // 멤버 등록
//        Member admin = memberService.createMember("admin", "1234", "admin@test.com", "adminName", null, null, "adminNick");
//        Member member1 = memberService.createMember("user1", "1234", "test1@test.com", "user1Name", null, null, "user1Nick");
//        Member member2 = memberService.createMember("user2", "1234", "test2@test.com", "user2Name", null, null, "user2Nick");
//
//        // 장소 등록
//        // TODO 반환값 지우기 반환타입도 void로 바꾸기
//        Place place1 = placeService.save(new PlaceDto("장소1", "33.1, 37.1"));
//        Place place2 = placeService.save(new PlaceDto("장소2", "33.2, 37.2"));
//        Place place3 = placeService.save(new PlaceDto("장소3", "33.3, 37.3"));
//        Place place4 = placeService.save(new PlaceDto("장소4", "33.4, 37.4"));
//
//        // Reservation 업체 등록
//        Reservation reservationCompany1 = reservationService.createNewReservation(new ReservationCreateForm(admin.getUsername(), place1.getId(), ReservationType.RESTAURANT));
//        Reservation reservationCompany2 = reservationService.createNewReservation(new ReservationCreateForm(admin.getUsername(), place2.getId(), ReservationType.ACCOMMODATE));
//        Reservation reservationCompany3 = reservationService.createNewReservation(new ReservationCreateForm(admin.getUsername(), place3.getId(), ReservationType.LEISURE));
//
//        // CheckReservation 사용자 예약 등록
//        checkReservationService.checkReservation(reservationCompany1.getId(), member1, new CheckReservationReqDto("2024-02-01", "2024-02-01", "11:00", 55_000));
//        checkReservationService.checkReservation(reservationCompany2.getId(), member2, new CheckReservationReqDto("2024-01-31", "2024-01-31", "14:00", 100_000));
//    }
//}
