package com.ll.tourdemonde.payment.checkReservation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCheckReservation is a Querydsl query type for CheckReservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCheckReservation extends EntityPathBase<CheckReservation> {

    private static final long serialVersionUID = 449725864L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCheckReservation checkReservation = new QCheckReservation("checkReservation");

    public final com.ll.tourdemonde.global.jpa.QBaseEntity _super = new com.ll.tourdemonde.global.jpa.QBaseEntity(this);

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 5aeccfb (feat : 마이페이지 권한 없을 시 리다이렉트)
    public final NumberPath<Long> adultCount = createNumber("adultCount", Long.class);

    public final NumberPath<Long> childrenCount = createNumber("childrenCount", Long.class);

<<<<<<< HEAD
=======
>>>>>>> ce746f1 (feat : 마이페이지 수정)
=======
>>>>>>> 5aeccfb (feat : 마이페이지 권한 없을 시 리다이렉트)
=======
>>>>>>> c260145 (feat : 마이페이지 수정)
    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final com.ll.tourdemonde.payment.order.entity.QOrder order;

    public final com.ll.tourdemonde.reservation.entity.QReservationOption reservationOption;

    public QCheckReservation(String variable) {
        this(CheckReservation.class, forVariable(variable), INITS);
    }

    public QCheckReservation(Path<? extends CheckReservation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCheckReservation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCheckReservation(PathMetadata metadata, PathInits inits) {
        this(CheckReservation.class, metadata, inits);
    }

    public QCheckReservation(Class<? extends CheckReservation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new com.ll.tourdemonde.payment.order.entity.QOrder(forProperty("order"), inits.get("order")) : null;
        this.reservationOption = inits.isInitialized("reservationOption") ? new com.ll.tourdemonde.reservation.entity.QReservationOption(forProperty("reservationOption"), inits.get("reservationOption")) : null;
    }

}

