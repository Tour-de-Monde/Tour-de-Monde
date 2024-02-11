package com.ll.tourdemonde.payment.checkReservation.entity;

import com.ll.tourdemonde.global.jpa.BaseEntity;
import com.ll.tourdemonde.payment.order.entity.Order;
import com.ll.tourdemonde.reservation.entity.ReservationOption;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)

public class CheckReservation extends BaseEntity { // 회원의 예약 저장 상세
    @OneToOne(mappedBy = "checkReservation", cascade = CascadeType.ALL) // OneToOne 관계 설정
    private Order order; // 예약한 사용자의 주문번호, null일 경우 결제 전

    @ManyToOne
    @JoinColumn(name = "reservation_option_id")
    private ReservationOption reservationOption; // 예약한 사용자의 상세 옵션
}
