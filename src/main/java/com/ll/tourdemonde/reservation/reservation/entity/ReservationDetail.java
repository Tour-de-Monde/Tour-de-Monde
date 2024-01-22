package com.ll.tourdemonde.reservation.reservation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation_details")
public class ReservationDetail {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reservation_Id")
    private Reservation reservation;

    @NotNull
    private String type;

    @NotNull
    private String time;

}
