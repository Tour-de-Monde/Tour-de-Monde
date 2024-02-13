package com.ll.tourdemonde.reservation.dto;

import com.ll.tourdemonde.reservation.entity.ReservationType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationCreateForm {
    private String seller;
    private Long place;
    private ReservationType type;
    private String flag;
}
