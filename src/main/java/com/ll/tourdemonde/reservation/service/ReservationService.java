package com.ll.tourdemonde.reservation.service;

import com.ll.tourdemonde.global.rsData.RsData;
import com.ll.tourdemonde.global.util.Ut;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.service.PlaceService;
import com.ll.tourdemonde.reservation.dto.ReservationCreateForm;
import com.ll.tourdemonde.reservation.dto.ReservationOptionForm;
import com.ll.tourdemonde.reservation.entity.Reservation;
import com.ll.tourdemonde.reservation.entity.ReservationOption;
import com.ll.tourdemonde.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PlaceService placeService;

    @Transactional
    public Reservation createNewReservation(Place place, ReservationCreateForm form) {
        Reservation reservation = Reservation.builder()
                .place(place)
                .sellerName(form.getSeller())
                .type(form.getType())
                .build();
        return reservationRepository.save(reservation);
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));
    }

    @Transactional
    public Reservation createNewReservationOption(ReservationOptionForm form) {
        Reservation reservation = findById(form.getReservationId());

        // endDate가 없으면 시작일로 설정
        form.initEndDateIfNotExists();

        reservation.addOption(form);
        reservationRepository.save(reservation);
        return reservation;
    }

    public RsData<List<Reservation>> findAllByPlace(Place place) {
        List<Reservation> reservationList = reservationRepository.findAllByPlaceOrderByIdAsc(place);

        // 생성된 예약이 전혀 없을 때
        if (reservationList.isEmpty()) {
            return new RsData<>("S-NoSearch", "예약을 찾을 수 없습니다.", null);
        }

        return new RsData<>("S-searchList", "성공", reservationList);
    }

    @Transactional
    public RsData<Reservation> modifyReservation(Reservation reservation, ReservationCreateForm form) {
        // 셀러와 수정한 사람이 동일인인지 확인
        if (!reservation.getSellerName().equals(form.getSeller())) {
            return new RsData<>("f-modify", "권한이 없는 사용자입니다.", null);
        }

        reservation.setType(form.getType());

        return new RsData<>("S-modify", "성공", reservation);
    }

    public ReservationOption findOptionById(Long reservationId, Long optionId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."))
                .getOptions()
                .stream()
                .filter(opt -> opt.getId().equals(optionId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("옵션을 찾을 수 없습니다."));

    }

    @Transactional
    public ReservationOption modifyReservationOption(Long reservationId, Long optionId, ReservationOptionForm form) {
        // endDate 초기화
        form.initEndDateIfNotExists();

        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 입력입니다."))
                .getOptions().stream()
                .filter(opt -> opt.getId().equals(optionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("해당 옵션을 찾을 수 없습니다."))
                .modifyValues(
                        Ut.stringToLocalDateTime(form.getStartDate()),
                        Ut.stringToLocalDateTime(form.getEndDate()),
                        form.getTime(),
                        form.getPrice()
                );
    }

    @Transactional
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                        .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));

        reservationRepository.delete(reservation);
    }

    @Transactional
    public void deleteOption(Reservation reservation, Long optionId) {
        reservation.removeOption(optionId);
    }
}
