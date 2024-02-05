package com.ll.tourdemonde.payment.checkReservation.service;

import com.ll.tourdemonde.global.exception.GlobalException;
import com.ll.tourdemonde.global.rq.Rq;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.payment.checkReservation.dto.CheckReservationReqDto;
import com.ll.tourdemonde.payment.checkReservation.entity.CheckReservation;
import com.ll.tourdemonde.payment.checkReservation.repository.CheckReservationRepository;
import com.ll.tourdemonde.reservation.entity.Reservation;
import com.ll.tourdemonde.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckReservationService {
    private final CheckReservationRepository checkReservationRepository;
    private final ReservationRepository reservationRepository;
    private final Rq rq;

    @Transactional
    public CheckReservation checkReservation(Long reservationId, Member buyer, CheckReservationReqDto requestDto) {
        // 예약한 장소 찾기
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);

        if (reservation == null) {
            throw new GlobalException("400", "현재 장소에 등록된 예약이 없습니다.");
        }

        CheckReservation checkReservation = CheckReservation.builder()
                .reservationOption(reservation.getOptions().get(0))
                .order(null)
                .build();

        checkReservationRepository.save(checkReservation);

        return checkReservation;
    }

    public Optional<CheckReservation> findById(long id) {
        return checkReservationRepository.findById(id);
    }
}
