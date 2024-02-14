package com.ll.tourdemonde.reservation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservationOption is a Querydsl query type for ReservationOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservationOption extends EntityPathBase<ReservationOption> {

    private static final long serialVersionUID = 2078496499L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservationOption reservationOption = new QReservationOption("reservationOption");

    public final com.ll.tourdemonde.post.entity.QBaseTime _super = new com.ll.tourdemonde.post.entity.QBaseTime(this);

    public final NumberPath<Long> adultPrice = createNumber("adultPrice", Long.class);

    public final NumberPath<Long> childrenPrice = createNumber("childrenPrice", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final BooleanPath occupied = createBoolean("occupied");

    public final QReservation reservation;

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public final TimePath<java.time.LocalTime> time = createTime("time", java.time.LocalTime.class);

    public QReservationOption(String variable) {
        this(ReservationOption.class, forVariable(variable), INITS);
    }

    public QReservationOption(Path<? extends ReservationOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservationOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservationOption(PathMetadata metadata, PathInits inits) {
        this(ReservationOption.class, metadata, inits);
    }

    public QReservationOption(Class<? extends ReservationOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reservation = inits.isInitialized("reservation") ? new QReservation(forProperty("reservation"), inits.get("reservation")) : null;
    }

}

