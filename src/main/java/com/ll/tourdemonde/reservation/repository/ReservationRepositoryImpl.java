package com.ll.tourdemonde.reservation.repository;

import com.ll.tourdemonde.reservation.entity.ReservationOption;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;

import static com.ll.tourdemonde.reservation.entity.QReservationOption.reservationOption;

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReservationOption> search(Long placeId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        // 조건 설정
        builder.and(reservationOption.reservation.place.id.eq(placeId));
        builder.and(
                reservationOption.startDate.between(startDate, endDate)
                        .or(reservationOption.endDate.between(startDate, endDate)));

        JPAQuery<ReservationOption> query = queryFactory.select(reservationOption)
                .from(reservationOption)
//                .leftJoin(reservation).on(reservationOption.reservation.eq(reservation))
                .where(builder)
                .orderBy(reservationOption.id.asc());

        query.offset(pageable.getOffset()).limit(pageable.getPageSize());

        JPAQuery<Long> totalQuery = queryFactory
                .select(reservationOption.count())
                .from(reservationOption)
//                .leftJoin(reservation).on(reservationOption.reservation.eq(reservation))
                .where(builder);

        return PageableExecutionUtils.getPage(query.fetch(), pageable, totalQuery::fetchOne);

    }
}
