package com.app.treen.products.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWishCategory is a Querydsl query type for WishCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWishCategory extends EntityPathBase<WishCategory> {

    private static final long serialVersionUID = 1711079454L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWishCategory wishCategory = new QWishCategory("wishCategory");

    public final QCategory category;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QTradeProduct tradeProduct;

    public QWishCategory(String variable) {
        this(WishCategory.class, forVariable(variable), INITS);
    }

    public QWishCategory(Path<? extends WishCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWishCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWishCategory(PathMetadata metadata, PathInits inits) {
        this(WishCategory.class, metadata, inits);
    }

    public QWishCategory(Class<? extends WishCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.tradeProduct = inits.isInitialized("tradeProduct") ? new QTradeProduct(forProperty("tradeProduct"), inits.get("tradeProduct")) : null;
    }

}

