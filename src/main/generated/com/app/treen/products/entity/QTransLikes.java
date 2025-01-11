package com.app.treen.products.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTransLikes is a Querydsl query type for TransLikes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTransLikes extends EntityPathBase<TransLikes> {

    private static final long serialVersionUID = 625283469L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTransLikes transLikes = new QTransLikes("transLikes");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QTransProduct transProduct;

    public final com.app.treen.user.entity.QUser user;

    public QTransLikes(String variable) {
        this(TransLikes.class, forVariable(variable), INITS);
    }

    public QTransLikes(Path<? extends TransLikes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTransLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTransLikes(PathMetadata metadata, PathInits inits) {
        this(TransLikes.class, metadata, inits);
    }

    public QTransLikes(Class<? extends TransLikes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.transProduct = inits.isInitialized("transProduct") ? new QTransProduct(forProperty("transProduct"), inits.get("transProduct")) : null;
        this.user = inits.isInitialized("user") ? new com.app.treen.user.entity.QUser(forProperty("user")) : null;
    }

}

