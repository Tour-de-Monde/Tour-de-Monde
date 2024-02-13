package com.ll.tourdemonde.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostPlace is a Querydsl query type for PostPlace
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostPlace extends EntityPathBase<PostPlace> {

    private static final long serialVersionUID = -1902677073L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostPlace postPlace = new QPostPlace("postPlace");

    public final QBaseTime _super = new QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final com.ll.tourdemonde.place.entity.QPlace place;

    public final QPost post;

    public final QPostPlacePlaceReview postPlacePlaceReview;

    public QPostPlace(String variable) {
        this(PostPlace.class, forVariable(variable), INITS);
    }

    public QPostPlace(Path<? extends PostPlace> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostPlace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostPlace(PathMetadata metadata, PathInits inits) {
        this(PostPlace.class, metadata, inits);
    }

    public QPostPlace(Class<? extends PostPlace> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.place = inits.isInitialized("place") ? new com.ll.tourdemonde.place.entity.QPlace(forProperty("place")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
        this.postPlacePlaceReview = inits.isInitialized("postPlacePlaceReview") ? new QPostPlacePlaceReview(forProperty("postPlacePlaceReview"), inits.get("postPlacePlaceReview")) : null;
    }

}

