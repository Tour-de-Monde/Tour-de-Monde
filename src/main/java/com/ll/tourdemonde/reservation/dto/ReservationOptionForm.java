package com.ll.tourdemonde.reservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationOptionForm {
    private Long reservationId;

    private String startDate;

    private String endDate;

    @NotNull
    private String time;

    @NotNull
    private Long price;

    public boolean hasEndDate() {
        if (endDate == null) {
            return false;
        }
        return !endDate.isBlank();
    }

    public void initEndDateIfNotExists(){
        if (!hasEndDate()) {
            this.endDate = startDate;
        }
    }
}
