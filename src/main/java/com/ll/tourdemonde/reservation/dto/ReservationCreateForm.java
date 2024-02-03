package com.ll.tourdemonde.reservation.dto;

import com.ll.tourdemonde.reservation.entity.ReservationType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // TODO 희영님 코드라서 지워야함
public class ReservationCreateForm {
    private String seller;
    private Long place;
    private ReservationType type;
}
