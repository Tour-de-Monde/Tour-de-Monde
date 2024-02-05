package com.ll.tourdemonde.payment.checkReservation.repository;

import com.ll.tourdemonde.payment.checkReservation.entity.CheckReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckReservationRepository extends JpaRepository<CheckReservation, Long> {
}
