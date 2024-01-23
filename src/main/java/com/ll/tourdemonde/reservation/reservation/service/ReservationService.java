package com.ll.tourdemonde.reservation.reservation.service;

import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.service.PlaceService;
import com.ll.tourdemonde.reservation.reservation.dto.ReservationCreateForm;
import com.ll.tourdemonde.reservation.reservation.entity.Reservation;
import com.ll.tourdemonde.reservation.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PlaceService placeService;

    public Reservation createNewReservation(ReservationCreateForm form) {
        Place place = placeService.findById(form.getPlace());
        Reservation reservation = Reservation.builder()
                .place(place)
                .sellerName(form.getSeller())
                .type(form.getType())
                .build();
        return reservationRepository.save(reservation);
    }
}
