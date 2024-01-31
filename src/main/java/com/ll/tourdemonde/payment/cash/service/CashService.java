package com.ll.tourdemonde.payment.cash.service;

import com.ll.tourdemonde.payment.cash.repository.CashLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CashService {
    private final CashLogRepository cashLogRepository;
}
