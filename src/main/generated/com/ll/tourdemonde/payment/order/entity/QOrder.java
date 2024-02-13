package com.ll.tourdemonde.payment.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = 1663935430L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final com.ll.tourdemonde.global.jpa.QBaseEntity _super = new com.ll.tourdemonde.global.jpa.QBaseEntity(this);

    public final com.ll.tourdemonde.member.entity.QMember buyer;

    public final DateTimePath<java.time.LocalDateTime> cancelDate = createDateTime("cancelDate", java.time.LocalDateTime.class);

    public final com.ll.tourdemonde.payment.checkReservation.entity.QCheckReservation checkReservation;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final DateTimePath<java.time.LocalDateTime> payDate = createDateTime("payDate", java.time.LocalDateTime.class);

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    public final NumberPath<Long> price = createNumber("price", Long.class);

=======
>>>>>>> ce746f1 (feat : 마이페이지 수정)
=======
    public final NumberPath<Long> price = createNumber("price", Long.class);

>>>>>>> 5aeccfb (feat : 마이페이지 권한 없을 시 리다이렉트)
=======
>>>>>>> c260145 (feat : 마이페이지 수정)
    public final DateTimePath<java.time.LocalDateTime> refundDate = createDateTime("refundDate", java.time.LocalDateTime.class);

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.buyer = inits.isInitialized("buyer") ? new com.ll.tourdemonde.member.entity.QMember(forProperty("buyer")) : null;
        this.checkReservation = inits.isInitialized("checkReservation") ? new com.ll.tourdemonde.payment.checkReservation.entity.QCheckReservation(forProperty("checkReservation"), inits.get("checkReservation")) : null;
    }

}

