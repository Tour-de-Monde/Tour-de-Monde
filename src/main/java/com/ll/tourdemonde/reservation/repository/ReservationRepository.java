package com.ll.tourdemonde.reservation.repository;

import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByPlaceOrderByIdAsc(Place place);

}
