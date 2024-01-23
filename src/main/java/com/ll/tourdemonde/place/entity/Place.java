package com.ll.tourdemonde.place.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Place { // 장소 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 이름

    private String address; // 주소

//    @ElementCollection // 1:N의 매핑으로 테이블에 데이터가 저장 TODO Reservation 테이블 있으면 주석 해제
//    private List<Reservation> reservationExample.html; // 예약 현황

    private String coordinates; // 좌표(latitude(위도), longitude(경도)), 2자리 소수 리스트 필드

    private LocalDateTime createDate; // 생성시간

    public Place(String name, String address, String coordinates) {
        this.name = name;
        this.address = address;
        this.coordinates = coordinates;
        this.createDate = LocalDateTime.now();
    }
}
