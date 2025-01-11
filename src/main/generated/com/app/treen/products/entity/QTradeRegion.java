package com.app.treen.products.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTradeRegion is a Querydsl query type for TradeRegion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTradeRegion extends EntityPathBase<TradeRegion> {

    private static final long serialVersionUID = -1711467265L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTradeRegion tradeRegion = new QTradeRegion("tradeRegion");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRegion region;

    public final QTradeProduct tradeProduct;

    public QTradeRegion(String variable) {
        this(TradeRegion.class, forVariable(variable), INITS);
    }

    public QTradeRegion(Path<? extends TradeRegion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTradeRegion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTradeRegion(PathMetadata metadata, PathInits inits) {
        this(TradeRegion.class, metadata, inits);
    }

    public QTradeRegion(Class<? extends TradeRegion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.region = inits.isInitialized("region") ? new QRegion(forProperty("region")) : null;
        this.tradeProduct = inits.isInitialized("tradeProduct") ? new QTradeProduct(forProperty("tradeProduct"), inits.get("tradeProduct")) : null;
    }

}

