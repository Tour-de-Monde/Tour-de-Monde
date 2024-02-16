package com.ll.tourdemonde.payment.order.repository;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.payment.order.entity.Order;
import com.ll.tourdemonde.post.entity.Post;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    Page<Order> findAllByBuyer(Member member, Pageable pageable);
}
