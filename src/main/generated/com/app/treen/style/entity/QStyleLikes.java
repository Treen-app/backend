package com.app.treen.style.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStyleLikes is a Querydsl query type for StyleLikes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStyleLikes extends EntityPathBase<StyleLikes> {

    private static final long serialVersionUID = 781437185L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStyleLikes styleLikes = new QStyleLikes("styleLikes");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QStyle style;

    public final com.app.treen.user.entity.QUser user;

    public QStyleLikes(String variable) {
        this(StyleLikes.class, forVariable(variable), INITS);
    }

    public QStyleLikes(Path<? extends StyleLikes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStyleLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStyleLikes(PathMetadata metadata, PathInits inits) {
        this(StyleLikes.class, metadata, inits);
    }

    public QStyleLikes(Class<? extends StyleLikes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.style = inits.isInitialized("style") ? new QStyle(forProperty("style"), inits.get("style")) : null;
        this.user = inits.isInitialized("user") ? new com.app.treen.user.entity.QUser(forProperty("user")) : null;
    }

}

