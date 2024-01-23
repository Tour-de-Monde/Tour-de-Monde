package com.ll.tourdemonde.reservation.reservation.repository;

import com.ll.tourdemonde.reservation.reservation.entity.ReservationOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationOptionRepository extends JpaRepository<ReservationOption, Long> {
}
