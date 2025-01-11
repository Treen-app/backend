package com.app.treen.products.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTradeLikes is a Querydsl query type for TradeLikes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTradeLikes extends EntityPathBase<TradeLikes> {

    private static final long serialVersionUID = -60626863L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTradeLikes tradeLikes = new QTradeLikes("tradeLikes");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QTradeProduct tradeProduct;

    public final com.app.treen.user.entity.QUser user;

    public QTradeLikes(String variable) {
        this(TradeLikes.class, forVariable(variable), INITS);
    }

    public QTradeLikes(Path<? extends TradeLikes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTradeLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTradeLikes(PathMetadata metadata, PathInits inits) {
        this(TradeLikes.class, metadata, inits);
    }

    public QTradeLikes(Class<? extends TradeLikes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tradeProduct = inits.isInitialized("tradeProduct") ? new QTradeProduct(forProperty("tradeProduct"), inits.get("tradeProduct")) : null;
        this.user = inits.isInitialized("user") ? new com.app.treen.user.entity.QUser(forProperty("user")) : null;
    }

}

