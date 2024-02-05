package com.ll.tourdemonde.payment.order.repository;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.payment.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT e FROM Order e WHERE e.buyer = :member AND (e.payDate IS NULL OR e.payDate = :nullDate) ORDER BY e.payDate DESC")
    List<Order> findByMemberAndCreateDate(@Param("member") Member buyer, @Param("nullDate") String nullDate);
}

