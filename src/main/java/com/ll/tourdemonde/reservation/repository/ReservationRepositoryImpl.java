package com.ll.tourdemonde.reservation.repository;

import com.ll.tourdemonde.reservation.entity.Reservation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static com.ll.tourdemonde.reservation.entity.QReservation.reservation;

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public Page<Reservation> search(Long placeId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        // 조건 설정
        builder.and(reservation.place.id.eq(placeId));
        builder.and(
                reservation.options.any().startDate.between(startDate,endDate)
                        .or(reservation.options.any().endDate.between(startDate,endDate)));

        QueryResults<Reservation> results = queryFactory.selectFrom(reservation)
                .leftJoin(reservation.options).fetchJoin()
                .where(builder)
                .orderBy(reservation.options.any().startDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());

    }
}
