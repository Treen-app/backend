package com.app.treen.products.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTransRegion is a Querydsl query type for TransRegion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTransRegion extends EntityPathBase<TransRegion> {

    private static final long serialVersionUID = -1923083453L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTransRegion transRegion = new QTransRegion("transRegion");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRegion region;

    public final QTransProduct transProduct;

    public QTransRegion(String variable) {
        this(TransRegion.class, forVariable(variable), INITS);
    }

    public QTransRegion(Path<? extends TransRegion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTransRegion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTransRegion(PathMetadata metadata, PathInits inits) {
        this(TransRegion.class, metadata, inits);
    }

    public QTransRegion(Class<? extends TransRegion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.region = inits.isInitialized("region") ? new QRegion(forProperty("region")) : null;
        this.transProduct = inits.isInitialized("transProduct") ? new QTransProduct(forProperty("transProduct"), inits.get("transProduct")) : null;
    }

}

