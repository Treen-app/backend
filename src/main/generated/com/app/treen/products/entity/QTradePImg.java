package com.app.treen.products.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTradePImg is a Querydsl query type for TradePImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTradePImg extends EntityPathBase<TradePImg> {

    private static final long serialVersionUID = -1110245890L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTradePImg tradePImg = new QTradePImg("tradePImg");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgUrl = createString("imgUrl");

    public final BooleanPath isMain = createBoolean("isMain");

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    public final QTradeProduct tradeProduct;

    public QTradePImg(String variable) {
        this(TradePImg.class, forVariable(variable), INITS);
    }

    public QTradePImg(Path<? extends TradePImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTradePImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTradePImg(PathMetadata metadata, PathInits inits) {
        this(TradePImg.class, metadata, inits);
    }

    public QTradePImg(Class<? extends TradePImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tradeProduct = inits.isInitialized("tradeProduct") ? new QTradeProduct(forProperty("tradeProduct"), inits.get("tradeProduct")) : null;
    }

}

