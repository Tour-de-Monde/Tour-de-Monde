package com.ll.tourdemonde.reservationOrder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class ReservationOrder {
    @Id
    @GeneratedValue
    private Long id;

    private String buyerName;

    @CreationTimestamp
    private LocalDateTime createDate;

    private LocalDateTime paidDate;

    private LocalDateTime cancleDate;

    private LocalDateTime refundDate;
}