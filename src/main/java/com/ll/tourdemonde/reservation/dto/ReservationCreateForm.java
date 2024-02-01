package com.ll.tourdemonde.reservation.dto;

import com.ll.tourdemonde.reservation.entity.ReservationType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // TODO 커밋 전에 지우기, 테스트 다하고 지우기
public class ReservationCreateForm {
    private String seller;
    private Long place;
    private ReservationType type;
}
