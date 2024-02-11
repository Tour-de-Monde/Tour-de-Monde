package com.ll.tourdemonde.payment.cash.service;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.payment.cash.entity.CashLog;
import com.ll.tourdemonde.payment.cash.repository.CashLogRepository;
import com.ll.tourdemonde.payment.order.entity.Order;
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

    public CashLog addCash(Member member, long price, CashLog.EventType eventType, Order order) {
        CashLog cashLog = CashLog.builder()
                .member(member)
                .price(price)
                .relTypeCode(order.getType())
                .relId(order.getId())
                .eventType(eventType)
                .build();

        cashLogRepository.save(cashLog);

        return cashLog;
    }
}
