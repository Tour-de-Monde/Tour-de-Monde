package com.ll.tourdemonde.payment.order.dto;

import lombok.Data;

@Data
public class OrderReservationReqDto {
    private Long adultCount;
    private Long childrenCount;
}
