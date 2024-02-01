package com.ll.tourdemonde.reservation.checkReservation.repository;

import com.ll.tourdemonde.reservation.checkReservation.entity.CheckReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckReservationRepository extends JpaRepository<CheckReservation, Long> {
}
