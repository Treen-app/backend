package com.app.treen.style.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStyle is a Querydsl query type for Style
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStyle extends EntityPathBase<Style> {

    private static final long serialVersionUID = 1507327867L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStyle style = new QStyle("style");

    public final com.app.treen.QBaseTimeEntity _super = new com.app.treen.QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> likeCount = createNumber("likeCount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final com.app.treen.user.entity.QUser user;

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QStyle(String variable) {
        this(Style.class, forVariable(variable), INITS);
    }

    public QStyle(Path<? extends Style> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStyle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStyle(PathMetadata metadata, PathInits inits) {
        this(Style.class, metadata, inits);
    }

    public QStyle(Class<? extends Style> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.app.treen.user.entity.QUser(forProperty("user")) : null;
    }

}

