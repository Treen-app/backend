package com.app.treen.trade.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTradeOffer is a Querydsl query type for TradeOffer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTradeOffer extends EntityPathBase<TradeOffer> {

    private static final long serialVersionUID = 1743753409L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTradeOffer tradeOffer = new QTradeOffer("tradeOffer");

    public final com.app.treen.QBaseTimeEntity _super = new com.app.treen.QBaseTimeEntity(this);

    public final com.app.treen.user.entity.QUser buyer;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAccomplished = createBoolean("isAccomplished");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final ListPath<OfferedProduct, QOfferedProduct> offeredProductList = this.<OfferedProduct, QOfferedProduct>createList("offeredProductList", OfferedProduct.class, QOfferedProduct.class, PathInits.DIRECT2);

    public final com.app.treen.products.entity.QTradeProduct salesProduct;

    public final com.app.treen.user.entity.QUser seller;

    public QTradeOffer(String variable) {
        this(TradeOffer.class, forVariable(variable), INITS);
    }

    public QTradeOffer(Path<? extends TradeOffer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTradeOffer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTradeOffer(PathMetadata metadata, PathInits inits) {
        this(TradeOffer.class, metadata, inits);
    }

    public QTradeOffer(Class<? extends TradeOffer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.buyer = inits.isInitialized("buyer") ? new com.app.treen.user.entity.QUser(forProperty("buyer")) : null;
        this.salesProduct = inits.isInitialized("salesProduct") ? new com.app.treen.products.entity.QTradeProduct(forProperty("salesProduct"), inits.get("salesProduct")) : null;
        this.seller = inits.isInitialized("seller") ? new com.app.treen.user.entity.QUser(forProperty("seller")) : null;
    }

}

