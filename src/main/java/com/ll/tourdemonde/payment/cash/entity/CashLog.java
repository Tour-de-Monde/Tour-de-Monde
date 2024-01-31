package com.ll.tourdemonde.payment.cash.entity;

import com.ll.tourdemonde.global.jpa.BaseEntity;
import com.ll.tourdemonde.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Setter
@Getter
@ToString(callSuper = true)
public class CashLog extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private String relTypeCode; // Reservation 타입
    private Long relId; // 주문 번호
    @ManyToOne
    private Member member;
    private long price;

    public enum EventType {
        충전__무통장입금,
        충전__토스페이먼츠,
        출금__통장입금,
        사용__토스페이먼츠_주문결제,
        사용__예치금_주문결제,
        환불__예치금_주문결제,
    }
}