package com.ll.tourdemonde.payment.order.dto;

import lombok.Data;

@Data
public class OrderReqDto {
    private String customerEmail;
    private String customerName;
    private String customerMobilePhone;
}
