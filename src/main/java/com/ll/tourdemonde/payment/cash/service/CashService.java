package com.ll.tourdemonde.payment.cash.service;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.payment.cash.entity.CashLog;
import com.ll.tourdemonde.payment.cash.repository.CashLogRepository;
import com.ll.tourdemonde.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CashService {
    private final CashLogRepository cashLogRepository;

    // 캐시 로그 저장
    @Transactional
    public CashLog addCash(Member member, long price, CashLog.EventType eventType, Reservation reservation) {
        CashLog cashLog = CashLog.builder()
                .member(member)
                .price(price)
                .relTypeCode(reservation.getType().name())
                .eventType(eventType)
                .build();

        cashLogRepository.save(cashLog);

        return cashLog;
    }
}
