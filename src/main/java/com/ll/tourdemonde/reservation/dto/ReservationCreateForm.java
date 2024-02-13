package com.ll.tourdemonde.reservation.dto;

import com.ll.tourdemonde.reservation.entity.ReservationType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservationCreateForm {
    private String seller;
    private Long place;
    private ReservationType type;
    private String flag;

    public ReservationCreateForm(String seller, Long place, ReservationType type, String flag) {
        this.seller = seller;
        this.place = place;
        this.type = type;
        this.flag = flag;
    }
}
