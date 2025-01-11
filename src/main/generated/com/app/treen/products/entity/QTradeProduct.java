package com.app.treen.products.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTradeProduct is a Querydsl query type for TradeProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTradeProduct extends EntityPathBase<TradeProduct> {

    private static final long serialVersionUID = 1383505988L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTradeProduct tradeProduct = new QTradeProduct("tradeProduct");

    public final com.app.treen.QBaseTimeEntity _super = new com.app.treen.QBaseTimeEntity(this);

    public final QCategory category;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath detail = createString("detail");

    public final EnumPath<com.app.treen.products.entity.enumeration.Gender> gender = createEnum("gender", com.app.treen.products.entity.enumeration.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<TradePImg, QTradePImg> images = this.<TradePImg, QTradePImg>createList("images", TradePImg.class, QTradePImg.class, PathInits.DIRECT2);

    public final NumberPath<Long> likedCount = createNumber("likedCount", Long.class);

    public final EnumPath<com.app.treen.products.entity.enumeration.Method> method = createEnum("method", com.app.treen.products.entity.enumeration.Method.class);

    public final StringPath name = createString("name");

    public final EnumPath<com.app.treen.products.entity.enumeration.Size> size = createEnum("size", com.app.treen.products.entity.enumeration.Size.class);

    public final EnumPath<com.app.treen.products.entity.enumeration.TradeType> tradeType = createEnum("tradeType", com.app.treen.products.entity.enumeration.TradeType.class);

    public final EnumPath<com.app.treen.products.entity.enumeration.Status> transactionStatus = createEnum("transactionStatus", com.app.treen.products.entity.enumeration.Status.class);

    public final EnumPath<com.app.treen.products.entity.enumeration.UsedRank> usedRank = createEnum("usedRank", com.app.treen.products.entity.enumeration.UsedRank.class);

    public final StringPath usedTerm = createString("usedTerm");

    public final com.app.treen.user.entity.QUser user;

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public final ListPath<WishCategory, QWishCategory> wishCategories = this.<WishCategory, QWishCategory>createList("wishCategories", WishCategory.class, QWishCategory.class, PathInits.DIRECT2);

    public final StringPath wishColor = createString("wishColor");

    public final EnumPath<com.app.treen.products.entity.enumeration.Size> wishSize = createEnum("wishSize", com.app.treen.products.entity.enumeration.Size.class);

    public QTradeProduct(String variable) {
        this(TradeProduct.class, forVariable(variable), INITS);
    }

    public QTradeProduct(Path<? extends TradeProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTradeProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTradeProduct(PathMetadata metadata, PathInits inits) {
        this(TradeProduct.class, metadata, inits);
    }

    public QTradeProduct(Class<? extends TradeProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.user = inits.isInitialized("user") ? new com.app.treen.user.entity.QUser(forProperty("user")) : null;
    }

}

