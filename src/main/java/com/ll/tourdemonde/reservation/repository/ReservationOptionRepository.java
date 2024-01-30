package com.ll.tourdemonde.reservation.repository;

import com.ll.tourdemonde.reservation.entity.ReservationOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationOptionRepository extends JpaRepository<ReservationOption, Long> {
}
