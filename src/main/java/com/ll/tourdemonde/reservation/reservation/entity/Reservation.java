package com.ll.tourdemonde.reservation.reservation.entity;

import com.ll.tourdemonde.place.entity.Place;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
    @Enumerated(value = EnumType.STRING) // String 형태로 데이터 저장, 기본은 enum의 순서를 명시(0,1,...)
    private ReservationType type;

    @Builder.Default
    @OneToMany(mappedBy = "reservation",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ReservationOption> options = new ArrayList<>();

    @OneToOne
    private Place place;


    public void addOption(ReservationOption option) {
        this.options.add(option);
    }
}
