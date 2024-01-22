package com.ll.tourdemonde.reservation.reservation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation_details")
public class ReservationDetail {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reservation_Id")
    private Reservation reservation;

    @NotNull(message = "시작날짜는 빈칸일 수 없습니다.")
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @NotNull
    private String time;

    @NotNull
    private Long price;

}
