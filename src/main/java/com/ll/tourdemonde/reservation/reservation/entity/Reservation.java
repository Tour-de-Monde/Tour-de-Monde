package com.ll.tourdemonde.reservation.reservation.entity;

import com.ll.tourdemonde.place.entity.Place;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

//    @OneToMany // 추후 변경 필요
    private String sellerName;

    @NotNull
    private ReservationType type;

    @OneToMany(mappedBy = "reservation")
    private List<ReservationDetail> details;

    @OneToOne
    private Place place;

}
