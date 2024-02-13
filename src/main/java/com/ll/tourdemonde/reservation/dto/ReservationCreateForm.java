package com.ll.tourdemonde.reservation.dto;

import com.ll.tourdemonde.reservation.entity.ReservationType;
import lombok.Data;

@Data
public class ReservationCreateForm {
    private String seller;
    private Long place;
    private ReservationType type;
    private String flag;
}
