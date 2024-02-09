package com.ll.tourdemonde.payment.order.entity;

import com.ll.tourdemonde.global.app.AppConfig;
import com.ll.tourdemonde.global.jpa.BaseEntity;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.payment.checkReservation.entity.CheckReservation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
@Table(name = "order_") // order는 SQL의 예약어로 따로 테이블명을 명시
public class Order extends BaseEntity { // 회원의 예약 저장
    @ManyToOne
    private Member buyer;

    @OneToOne
    @JoinColumn(name = "check_reservation_id")
    private CheckReservation checkReservation; // 하나의 주문에 1개의 예약

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

    // 장소의 타입 - LEISURE, ACCOMMODATE, RESTAURANT
    public String getType() {
        return this.getCheckReservation().getReservationOption().getReservation().getType().name();
    }

    // 장소의 이름
    public String getPlaceName() {
        return this.getCheckReservation().getReservationOption().getReservation().getPlace().getName();
    }

    // 예약한 장소의 가격
    public Long getPrice() {
        return this.getCheckReservation().getReservationOption().getPrice();
    }

    public boolean isCancelable() {
        if (cancelDate != null) return false;

        // 결제일자로부터 1시간 지나면 취소 불가능
        if (payDate != null && payDate.plusSeconds(AppConfig.getOrderCancelableSeconds()).isBefore(LocalDateTime.now())) return false;

        return true;
    }

    public String getCode() {
        // yyyy-MM-dd 형식의 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // LocalDateTime 객체를 문자열로 변환
        return getCreateDate().format(formatter) + (AppConfig.isNotProd() ? "-test-" + UUID.randomUUID() : "") + "__" + getId();
    }

    public String getForPrintPayStatus() {
        if (payDate != null)
            return "결제완료(" + payDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ")";

        if (cancelDate != null) return "-";

        return "결제대기";
    }

    public String getForPrintCancelStatus() {
        if (cancelDate != null)
            return "취소완료(" + payDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ")";

        if (!isCancelable()) return "취소불가능";

        return "취소가능";
    }

    public String getForPrintRefundStatus() {
        if (refundDate != null)
            return "환불완료(" + payDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ")";

        if (payDate == null) return "-";
        if (!isCancelable()) return "-";

        return "환불가능";
    }

    public boolean isPayDone() {
        return payDate != null;
    }
}
