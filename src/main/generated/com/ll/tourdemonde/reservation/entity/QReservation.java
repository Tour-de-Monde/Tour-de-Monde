package com.ll.tourdemonde.reservation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservation is a Querydsl query type for Reservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservation extends EntityPathBase<Reservation> {

    private static final long serialVersionUID = 1919713502L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservation reservation = new QReservation("reservation");

    public final com.ll.tourdemonde.post.entity.QBaseTime _super = new com.ll.tourdemonde.post.entity.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final ListPath<ReservationOption, QReservationOption> options = this.<ReservationOption, QReservationOption>createList("options", ReservationOption.class, QReservationOption.class, PathInits.DIRECT2);

    public final com.ll.tourdemonde.place.entity.QPlace place;

    public final com.ll.tourdemonde.member.entity.QMember seller;

    public final EnumPath<ReservationType> type = createEnum("type", ReservationType.class);

    public QReservation(String variable) {
        this(Reservation.class, forVariable(variable), INITS);
    }

    public QReservation(Path<? extends Reservation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservation(PathMetadata metadata, PathInits inits) {
        this(Reservation.class, metadata, inits);
    }

    public QReservation(Class<? extends Reservation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.place = inits.isInitialized("place") ? new com.ll.tourdemonde.place.entity.QPlace(forProperty("place")) : null;
        this.seller = inits.isInitialized("seller") ? new com.ll.tourdemonde.member.entity.QMember(forProperty("seller")) : null;
    }

}

