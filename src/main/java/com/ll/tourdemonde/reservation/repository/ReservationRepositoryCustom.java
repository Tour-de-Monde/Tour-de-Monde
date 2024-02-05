package com.ll.tourdemonde.reservation.repository;

import com.ll.tourdemonde.reservation.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ReservationRepositoryCustom {
    Page<Reservation> search(Long placeId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
