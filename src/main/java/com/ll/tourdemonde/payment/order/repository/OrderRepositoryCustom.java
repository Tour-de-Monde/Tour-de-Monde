package com.ll.tourdemonde.payment.order.repository;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.payment.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {
    Page<Order> search(Member buyer, Boolean payStatus, Boolean cancelStatus, Boolean refundStatus, Pageable pageable);
}
