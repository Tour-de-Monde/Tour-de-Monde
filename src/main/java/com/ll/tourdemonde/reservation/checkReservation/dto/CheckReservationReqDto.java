package com.ll.tourdemonde.reservation.checkReservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckReservationReqDto {
    @NotNull(message = "시작 날짜는 빈칸일 수 없습니다.")
    private String startDate; // 예약 날짜
    private String endDate;
    @NotNull
    private String time; // 예약 시간
    @NotNull
    private long price; // 가격
}
