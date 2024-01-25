package com.ll.tourdemonde.reservation.reservation.service;

import com.ll.tourdemonde.global.rsData.RsData;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.service.PlaceService;
import com.ll.tourdemonde.reservation.reservation.dto.ReservationCreateForm;
import com.ll.tourdemonde.reservation.reservation.dto.ReservationOptionForm;
import com.ll.tourdemonde.reservation.reservation.entity.Reservation;
import com.ll.tourdemonde.reservation.reservation.entity.ReservationOption;
import com.ll.tourdemonde.reservation.reservation.repository.ReservationOptionRepository;
import com.ll.tourdemonde.reservation.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationOptionRepository reservationOptionRepository;
    private final PlaceService placeService;

    @Transactional
    public Reservation createNewReservation(ReservationCreateForm form) {
        Place place = placeService.findById(form.getPlace());
        Reservation reservation = Reservation.builder()
                .place(place)
                .sellerName(form.getSeller())
                .type(form.getType())
                .build();
        return reservationRepository.save(reservation);
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 입력입니다."));
    }

    @Transactional
    public ReservationOption createNewReservationOption(ReservationOptionForm form) {
        Reservation reservation = findById(form.getReservationId());

        // endDate가 없으면 시작일로 설정
        form.initEndDateIfNotExists();

        ReservationOption reservationOption = ReservationOption.builder()
                .reservation(reservation)
                .startDate(StringToLocalDateTime(form.getStartDate()))
                .endDate(StringToLocalDateTime(form.getEndDate()))
                .time(form.getTime())
                .price(form.getPrice())
                .build();

        reservation.addOption(reservationOption);
        reservationRepository.save(reservation);
        return reservationOption;
    }

    public LocalDateTime StringToLocalDateTime(String string){
        // html input은 String으로 날짜만 반환한다. ex) 2024-01-24
        LocalDate date = LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE);
        // 00시 00분으로 설정
        LocalTime time = LocalTime.of(0,0,0);
        // LocalDateTime으로 parse
        return date.atTime(time);
    }

    public RsData<List<Reservation>> findAllByPlace(Place place) {
        List<Reservation> reservationList = reservationRepository.findAllByPlaceOrderByIdAsc(place);

        // 생성된 예약이 전혀 없을 때
        if (reservationList.isEmpty()){
            return new RsData<>("F", "예약을 찾을 수 없습니다.", null);
        }

        return new RsData<>("S-searchList", "성공", reservationList);
    }

    @Transactional
    public RsData<Reservation> modifyReservation(Reservation reservation, ReservationCreateForm form) {
        // 셀러와 수정한 사람이 동일인인지 확인
        if(reservation.getSellerName().equals(form.getSeller())){
            throw new RuntimeException("권한이 없는 사용자입니다.");
        }

        reservation.setType(form.getType());

        return new RsData<>("S-modify", "성공", reservation);
    }

    public ReservationOption findOptionById(Long id) {
        return reservationOptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 입력입니다."));

    }

    @Transactional
    public ReservationOption modifyReservationOption(Long optionId, ReservationOptionForm form) {
        ReservationOption option = reservationRepository.findById(form.getReservationId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 입력입니다."))
                .getOptions().stream()
                .filter(opt -> opt.getId().equals(optionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("해당 옵션을 찾을 수 없습니다."));

        // endDate 초기화
        form.initEndDateIfNotExists();

        option.modifyValues(
                StringToLocalDateTime(form.getStartDate()),
                StringToLocalDateTime(form.getEndDate()),
                form.getTime(), form.getPrice());

        reservationOptionRepository.save(option);
        return option;
    }
}
