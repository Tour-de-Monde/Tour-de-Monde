package com.ll.tourdemonde.place.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

//    @ElementCollection // 1:N의 매핑으로 테이블에 데이터가 저장
//    private List<Reservation> reservation; // 예약 현황

    @ElementCollection // 1:N의 매핑으로 테이블에 데이터가 저장
    private List<String> coordiate; // [latitude(위도), longitude(경도)], 2자리 소수 리스트 필드
}
