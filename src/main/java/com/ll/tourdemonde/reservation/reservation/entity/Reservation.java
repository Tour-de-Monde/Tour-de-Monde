package com.ll.tourdemonde.reservation.reservation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

//    @OneToMany // 추후 변경 필요
    private String sellerName;

    @NotNull(message = "시작날짜는 빈칸일 수 없습니다.")
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long price;

    @OneToMany(mappedBy = "reservation")
    private List<ReservationDetail> details;
}
