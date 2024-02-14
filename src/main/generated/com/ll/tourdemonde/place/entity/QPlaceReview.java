package com.ll.tourdemonde.place.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaceReview is a Querydsl query type for PlaceReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaceReview extends EntityPathBase<PlaceReview> {

    private static final long serialVersionUID = -519738506L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaceReview placeReview = new QPlaceReview("placeReview");

    public final com.ll.tourdemonde.post.entity.QBaseTime _super = new com.ll.tourdemonde.post.entity.QBaseTime(this);

    public final com.ll.tourdemonde.member.entity.QMember author;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final QPlace place;

    public final NumberPath<Integer> rating = createNumber("rating", Integer.class);

    public final StringPath review = createString("review");

    public QPlaceReview(String variable) {
        this(PlaceReview.class, forVariable(variable), INITS);
    }

    public QPlaceReview(Path<? extends PlaceReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaceReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaceReview(PathMetadata metadata, PathInits inits) {
        this(PlaceReview.class, metadata, inits);
    }

    public QPlaceReview(Class<? extends PlaceReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.ll.tourdemonde.member.entity.QMember(forProperty("author")) : null;
        this.place = inits.isInitialized("place") ? new QPlace(forProperty("place")) : null;
    }

}

