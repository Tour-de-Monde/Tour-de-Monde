//package com.ll.tourdemonde.payment.checkReservation.init;
//
//import com.ll.tourdemonde.member.entity.Member;
//import com.ll.tourdemonde.member.service.MemberService;
//import com.ll.tourdemonde.payment.checkReservation.service.CheckReservationService;
//import com.ll.tourdemonde.payment.order.entity.Order;
//import com.ll.tourdemonde.payment.order.service.OrderService;
//import com.ll.tourdemonde.place.dto.PlaceDto;
//import com.ll.tourdemonde.place.entity.Place;
//import com.ll.tourdemonde.place.service.PlaceService;
//import com.ll.tourdemonde.reservation.dto.ReservationCreateForm;
//import com.ll.tourdemonde.reservation.dto.ReservationOptionForm;
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
//    private final OrderService orderService;
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
//        // 업체 등록
//        Reservation company1 = reservationService.createNewReservation(place1, new ReservationCreateForm(admin.getUsername(), place1.getId(), ReservationType.RESTAURANT));
//        Reservation company2 = reservationService.createNewReservation(place2, new ReservationCreateForm(admin.getUsername(), place2.getId(), ReservationType.ACCOMMODATE));
//        Reservation company3 = reservationService.createNewReservation(place3, new ReservationCreateForm(admin.getUsername(), place3.getId(), ReservationType.LEISURE));
//
//        // 업체 예약 등록
//        Reservation company1ReservationOp1 = reservationService.createNewReservationOption(new ReservationOptionForm(company1.getId(), "2024-02-04", "2024-02-04", "11:00", 50_000L));
//        Reservation company1ReservationOp2 = reservationService.createNewReservationOption(new ReservationOptionForm(company1.getId(), "2024-02-05", "2024-02-06", "11:00", 150_000L));
//        Reservation company1ReservationOp3 = reservationService.createNewReservationOption(new ReservationOptionForm(company1.getId(), "2024-02-06", "2024-02-06", "11:00", 150_000L));
//
//        Reservation company2ReservationOp1 = reservationService.createNewReservationOption(new ReservationOptionForm(company2.getId(), "2024-02-03", "2024-02-03", "11:00", 100_000L));
//        Reservation company2ReservationOp2 = reservationService.createNewReservationOption(new ReservationOptionForm(company2.getId(), "2024-02-04", "2024-02-05", "11:00", 150_000L));
//
//        // 사용자 예약 등록 - Order, CheckReservation 저장
//        Order order1 = checkReservationService.checkReservation(company2ReservationOp1.getId(), member1);
//        Order order2 = checkReservationService.checkReservation(company1ReservationOp1.getId(), member2);
//
//        // 토스페이먼츠 결제
//        orderService.payByTossPayments(order1, 100_000L);
//        orderService.payByTossPayments(order2, 50_000L);
//    }
//}
