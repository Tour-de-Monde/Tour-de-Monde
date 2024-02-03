package com.ll.tourdemonde.reservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // TODO 희영님 코드라서 지워야함
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

    public ReservationOptionForm initEndDateIfNotExists() {
        if (!hasEndDate()) {
            this.endDate = startDate;
        }
        return this;
    }
}
