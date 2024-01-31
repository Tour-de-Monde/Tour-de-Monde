package com.ll.tourdemonde.payment.cash.repository;

import com.ll.tourdemonde.payment.cash.entity.CashLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashLogRepository extends JpaRepository<CashLog, Long> {
}
