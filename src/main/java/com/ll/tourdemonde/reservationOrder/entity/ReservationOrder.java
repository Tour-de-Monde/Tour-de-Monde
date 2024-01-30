package com.ll.tourdemonde.reservationOrder.entity;

import com.ll.tourdemonde.reservation.entity.Reservation;
import jakarta.persistence.*;
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


    private Reservation reservation;

    @CreationTimestamp
    private LocalDateTime createDate;

    private LocalDateTime paidDate;

    private LocalDateTime cancleDate;

    private LocalDateTime refundDate;
}