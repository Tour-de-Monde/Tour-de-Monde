package com.ll.tourdemonde.reservation.checkReservation.entity;

import com.ll.tourdemonde.global.jpa.BaseEntity;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.payment.order.entity.Order;
import com.ll.tourdemonde.reservation.entity.Reservation;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
public class CheckReservation extends BaseEntity { // 회원의 예약 저장
    @ManyToOne
    private Member buyer; // 예약한 사용자

    @OneToOne
    private Order order; // 예약한 사용자의 주문번호, null일 경우 결제 전

    @ManyToOne
    private Reservation reservation; // 어떤 장소를 예약했는지

    @NotNull
    private String startDate; // 예약 날짜

    private String endDate;

    @NotNull
    private String time; // 예약 시간

    @NotNull
    private long price; // 가격
}
