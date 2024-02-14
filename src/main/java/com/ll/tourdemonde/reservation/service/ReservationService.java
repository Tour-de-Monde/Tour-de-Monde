package com.ll.tourdemonde.reservation.service;

import com.ll.tourdemonde.global.exception.GlobalException;
import com.ll.tourdemonde.global.rq.Rq;
import com.ll.tourdemonde.global.rsData.RsData;
import com.ll.tourdemonde.global.util.Ut;
import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.member.service.MemberService;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.reservation.dto.ReservationCreateForm;
import com.ll.tourdemonde.reservation.dto.ReservationOptionForm;
import com.ll.tourdemonde.reservation.entity.Reservation;
import com.ll.tourdemonde.reservation.entity.ReservationOption;
import com.ll.tourdemonde.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberService memberService;
    private final Rq rq;

    // 새로운 예약 생성
    @Transactional
    public RsData<Reservation> createNewReservation(Place place, ReservationCreateForm form) {
        try{
        Member seller = memberService.findByUsername(form.getSeller())
                .orElseThrow(()-> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        Reservation reservation = Reservation.builder()
                .place(place)
                .seller(seller)
                .type(form.getType())
                .build();
        Reservation data = reservationRepository.save(reservation);
        return new RsData<>("S-success", "예약 생성 성공", data);
        } catch (Exception e){
            throw new GlobalException("F-fail", "예약을 생성하지 못했습니다.");
        }
    }

    // Id로 예약 찾기
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));
    }

    // 새로운 예약 옵션 생성
    @Transactional
    public RsData<Reservation> createNewReservationOption(Long reservationId, ReservationOptionForm form) {
        try {
            Reservation reservation = findById(reservationId);

            // endDate가 없으면 시작일로 설정
            form.initEndDateIfNotExists();

            reservation.addOption(form);
            Reservation reservation1 = reservationRepository.save(reservation);

            return new RsData<>("S-success", "예약옵션 생성 성공", reservation1);
        } catch(Exception e){
            throw new GlobalException("F-fail", "예약옵션 생성 실패");
        }
    }


    // 장소에 포함된 모든 예약 찾기
    public RsData<List<Reservation>> findAllByPlace(Place place) {
        List<Reservation> reservationList = reservationRepository.findAllByPlaceOrderByIdAsc(place);

        // 생성된 예약이 전혀 없을 때
        if (reservationList.isEmpty()) {
            return new RsData<>("S-NoSearch", "예약을 찾을 수 없습니다.", null);
        }

        return new RsData<>("S-searchList", "성공", reservationList);
    }

    // 예약 수정
    @Transactional
    public RsData<Reservation> modifyReservation(Reservation reservation, ReservationCreateForm form) {

        reservation.setType(form.getType());

        return new RsData<>("S-modify", "수정 성공", reservation);
    }

    //예약 옵션 찾기
    public ReservationOption findOptionById(Long reservationId, Long optionId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."))
                .getOptions()
                .stream()
                .filter(opt -> opt.getId().equals(optionId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("옵션을 찾을 수 없습니다."));

    }

    //예약 옵션 수정
    @Transactional
    public ReservationOption modifyReservationOption(Long reservationId, Long optionId, ReservationOptionForm form) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 입력입니다."));

        // endDate 초기화
        form.initEndDateIfNotExists();

        return reservation
                .getOptions().stream()
                .filter(opt -> opt.getId().equals(optionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("해당 옵션을 찾을 수 없습니다."))
                .modifyValues(
                        Ut.stringToLocalDateTime(form.getStartDate()),
                        Ut.stringToLocalDateTime(form.getEndDate()),
                        LocalTime.parse(form.getTime()),
                        form.getAdultPrice(),
                        form.getChildrenPrice()
                );

    }

    // 예약 삭제
    @Transactional
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));

        reservationRepository.delete(reservation);
    }

    // 예약 옵션 삭제
    @Transactional
    public void deleteOption(Reservation reservation, Long optionId) {

        reservation.removeOption(optionId);
    }

    public RsData<Page<ReservationOption>> findByDates(int page, Long placeId, LocalDateTime startDate, LocalDateTime endDate) {
        // pageable
        int pageSize = 10;
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("startDate"));
        sorts.add(Sort.Order.desc("endDate"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));

        Page<ReservationOption> optionPage =
                reservationRepository.search(placeId, startDate, endDate, pageable);

        if(optionPage.isEmpty()){
            return new RsData<>("S-","없음", null);
        }

        return new RsData<>("S-", "성공", optionPage);
    }

    public void checkAuthoritiesOfReservation(Long reservationId) throws GlobalException{
        Member modifier = rq.getMember();

        Reservation reservation = findById(reservationId);

        if (!reservation.getSeller().equals(modifier) && !modifier.isAdmin()) {
            throw new GlobalException("F-NoAuthentication", "권한이 없는 사용자입니다.");
        }
    }
}
