package com.app.treen.style.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStyleScrap is a Querydsl query type for StyleScrap
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStyleScrap extends EntityPathBase<StyleScrap> {

    private static final long serialVersionUID = 787729686L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStyleScrap styleScrap = new QStyleScrap("styleScrap");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QStyle style;

    public final com.app.treen.user.entity.QUser user;

    public QStyleScrap(String variable) {
        this(StyleScrap.class, forVariable(variable), INITS);
    }

    public QStyleScrap(Path<? extends StyleScrap> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStyleScrap(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStyleScrap(PathMetadata metadata, PathInits inits) {
        this(StyleScrap.class, metadata, inits);
    }

    public QStyleScrap(Class<? extends StyleScrap> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.style = inits.isInitialized("style") ? new QStyle(forProperty("style"), inits.get("style")) : null;
        this.user = inits.isInitialized("user") ? new com.app.treen.user.entity.QUser(forProperty("user")) : null;
    }

}

