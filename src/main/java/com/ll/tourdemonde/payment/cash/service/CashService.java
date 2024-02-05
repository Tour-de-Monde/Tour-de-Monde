package com.ll.tourdemonde.payment.cash.service;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.payment.cash.entity.CashLog;
import com.ll.tourdemonde.payment.cash.repository.CashLogRepository;
import com.ll.tourdemonde.payment.checkReservation.entity.CheckReservation;
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
    public CashLog addCash(Member member, long price, CashLog.EventType eventType, CheckReservation checkReservation) {
        CashLog cashLog = CashLog.builder()
                .member(member)
                .price(price)
                // TODO reservationCheck 작성하기
//                .relTypeCode()
//                .relId()
                .eventType(eventType)
                .build();

        cashLogRepository.save(cashLog);

        return cashLog;
    }
}
