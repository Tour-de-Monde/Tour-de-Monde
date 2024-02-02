package com.ll.tourdemonde.reservation.entity;

import com.ll.tourdemonde.global.util.Ut;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.post.entity.BaseTime;
import com.ll.tourdemonde.reservation.dto.ReservationOptionForm;
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
public class Reservation extends BaseTime {
    @ManyToOne(fetch = FetchType.LAZY)
    private Member seller;

    @NotNull
    @Enumerated(value = EnumType.STRING) // String 형태로 데이터 저장, 기본은 enum의 순서를 명시(0,1,...)
    private ReservationType type;

    @Builder.Default
    @OneToMany(mappedBy = "reservation",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ReservationOption> options = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

    public void addOption(ReservationOptionForm form) {
        ReservationOption option = ReservationOption.builder()
                .reservation(this)
                .startDate(Ut.stringToLocalDateTime(form.getStartDate()))
                .endDate(Ut.stringToLocalDateTime(form.getEndDate()))
                .time(form.getTime())
                .price(form.getPrice())
                .build();
        options.add(option);
    }

    public void removeOption(Long optionId) {
        options.removeIf(option -> option.getId().equals(optionId));
    }

    public void setType(ReservationType type) {
        this.type = type;
    }

}
