package com.ll.tourdemonde.payment.order.entity;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.post.entity.BaseTime;
import com.ll.tourdemonde.reservation.entity.Reservation;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
@Table(name = "order_") // order는 SQL의 예약어로 따로 테이블명을 명시
public class Order extends BaseTime {
    @ManyToOne
    private Member buyer;

    @OneToOne // 하나의 주문에 1개의 예약
    private Reservation reservation;

    private LocalDateTime payDate; // 결제일
    private LocalDateTime cancelDate; // 취소일
    private LocalDateTime refundDate; // 환불일
}
