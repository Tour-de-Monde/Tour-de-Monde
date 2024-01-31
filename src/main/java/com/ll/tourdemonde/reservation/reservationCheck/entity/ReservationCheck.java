package com.ll.tourdemonde.reservation.reservationCheck.entity;

import com.ll.tourdemonde.global.jpa.BaseEntity;
import com.ll.tourdemonde.reservation.entity.Reservation;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
public class ReservationCheck extends BaseEntity {
    @ManyToOne
    private Reservation reservation;

    @NotNull(message = "시작 날짜는 빈칸일 수 없습니다.")
    private LocalDateTime startDate; // 예약 날짜

    private LocalDateTime endDate;

    @NotNull
    private String time; // 예약 시간

    @NotNull
    private long price; // 가격
}
