package com.ll.tourdemonde.payment.order.entity;

import com.ll.tourdemonde.global.jpa.BaseEntity;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.payment.checkReservation.entity.CheckReservation;
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
public class Order extends BaseEntity {
    @ManyToOne
    private Member buyer;

    @OneToOne // 하나의 주문에 1개의 예약
    private CheckReservation checkReservation;

    private LocalDateTime payDate; // 결제일
    private LocalDateTime cancelDate; // 취소일
    private LocalDateTime refundDate; // 환불일

    public void setPaymentDone() {
        payDate = LocalDateTime.now();
    }

    public void setCancelDone() {
        cancelDate = LocalDateTime.now();
    }

    public void setRefundDone() {
        refundDate = LocalDateTime.now();
    }

    // 결제 가능한 상태 확인
    public boolean isPayable() {
        if (payDate != null) return false; // 결제일이 null이 아닐경우 false
        if (cancelDate != null) return false; // 취소일이 null이 아닐경우 false

        return true; // 결제가 가능하다.
    }
}
