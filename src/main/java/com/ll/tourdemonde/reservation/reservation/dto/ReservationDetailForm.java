package com.ll.tourdemonde.reservation.reservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDetailForm {
    private Long reservationID;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @NotNull
    private String time;

    @NotNull
    private Long price;

}
