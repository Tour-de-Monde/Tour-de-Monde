package com.ll.tourdemonde.payment.order.repository;

import com.ll.tourdemonde.payment.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
