package com.ll.tourdemonde.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostPlacePlaceReview is a Querydsl query type for PostPlacePlaceReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostPlacePlaceReview extends EntityPathBase<PostPlacePlaceReview> {

    private static final long serialVersionUID = -1379795952L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostPlacePlaceReview postPlacePlaceReview = new QPostPlacePlaceReview("postPlacePlaceReview");

    public final QBaseTime _super = new QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final com.ll.tourdemonde.place.entity.QPlaceReview placeReview;

    public final QPostPlace postPlace;

    public QPostPlacePlaceReview(String variable) {
        this(PostPlacePlaceReview.class, forVariable(variable), INITS);
    }

    public QPostPlacePlaceReview(Path<? extends PostPlacePlaceReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostPlacePlaceReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostPlacePlaceReview(PathMetadata metadata, PathInits inits) {
        this(PostPlacePlaceReview.class, metadata, inits);
    }

    public QPostPlacePlaceReview(Class<? extends PostPlacePlaceReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.placeReview = inits.isInitialized("placeReview") ? new com.ll.tourdemonde.place.entity.QPlaceReview(forProperty("placeReview"), inits.get("placeReview")) : null;
        this.postPlace = inits.isInitialized("postPlace") ? new QPostPlace(forProperty("postPlace"), inits.get("postPlace")) : null;
    }

}

