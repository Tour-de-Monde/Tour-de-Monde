package com.ll.tourdemonde.payment.order.service;

import com.ll.tourdemonde.payment.order.entity.Order;
import com.ll.tourdemonde.payment.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    public Optional<Order> findById(long id) {
        return orderRepository.findById(id);
    }

    public boolean canPay(Order order, int pgPayPrice) {
        if (!order.isPayable()) return false; // 결제일, 취소일이 null이 아닌 경우 이미 주문을 했다.
    }
}
