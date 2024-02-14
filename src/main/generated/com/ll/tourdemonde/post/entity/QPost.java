package com.ll.tourdemonde.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -1017587656L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final QBaseTime _super = new QBaseTime(this);

    public final com.ll.tourdemonde.member.entity.QMember author;

    public final StringPath category = createString("category");

    public final ListPath<com.ll.tourdemonde.comment.entity.Comment, com.ll.tourdemonde.comment.entity.QComment> commentList = this.<com.ll.tourdemonde.comment.entity.Comment, com.ll.tourdemonde.comment.entity.QComment>createList("commentList", com.ll.tourdemonde.comment.entity.Comment.class, com.ll.tourdemonde.comment.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final ListPath<PostPlace, QPostPlace> postPlaces = this.<PostPlace, QPostPlace>createList("postPlaces", PostPlace.class, QPostPlace.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final NumberPath<Long> view = createNumber("view", Long.class);

    public final SetPath<com.ll.tourdemonde.member.entity.Member, com.ll.tourdemonde.member.entity.QMember> voter = this.<com.ll.tourdemonde.member.entity.Member, com.ll.tourdemonde.member.entity.QMember>createSet("voter", com.ll.tourdemonde.member.entity.Member.class, com.ll.tourdemonde.member.entity.QMember.class, PathInits.DIRECT2);

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.ll.tourdemonde.member.entity.QMember(forProperty("author")) : null;
    }

}

