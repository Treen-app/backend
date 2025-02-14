package com.app.treen.trade.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOfferedProduct is a Querydsl query type for OfferedProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOfferedProduct extends EntityPathBase<OfferedProduct> {

    private static final long serialVersionUID = -730873507L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOfferedProduct offeredProduct = new QOfferedProduct("offeredProduct");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAccepted = createBoolean("isAccepted");

    public final com.app.treen.products.entity.QTradeProduct product;

    public final QTradeOffer tradeOffer;

    public QOfferedProduct(String variable) {
        this(OfferedProduct.class, forVariable(variable), INITS);
    }

    public QOfferedProduct(Path<? extends OfferedProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOfferedProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOfferedProduct(PathMetadata metadata, PathInits inits) {
        this(OfferedProduct.class, metadata, inits);
    }

    public QOfferedProduct(Class<? extends OfferedProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.app.treen.products.entity.QTradeProduct(forProperty("product"), inits.get("product")) : null;
        this.tradeOffer = inits.isInitialized("tradeOffer") ? new QTradeOffer(forProperty("tradeOffer"), inits.get("tradeOffer")) : null;
    }

}

