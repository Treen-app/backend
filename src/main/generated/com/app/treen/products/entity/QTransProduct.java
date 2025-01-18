package com.app.treen.products.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTransProduct is a Querydsl query type for TransProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTransProduct extends EntityPathBase<TransProduct> {

    private static final long serialVersionUID = -881628544L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTransProduct transProduct = new QTransProduct("transProduct");

    public final com.app.treen.QBaseTimeEntity _super = new com.app.treen.QBaseTimeEntity(this);

    public final QCategory category;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath detail = createString("detail");

    public final EnumPath<com.app.treen.products.entity.enumeration.Gender> gender = createEnum("gender", com.app.treen.products.entity.enumeration.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<TransPImg, QTransPImg> images = this.<TransPImg, QTransPImg>createList("images", TransPImg.class, QTransPImg.class, PathInits.DIRECT2);

    public final NumberPath<Long> likedCount = createNumber("likedCount", Long.class);

    public final EnumPath<com.app.treen.products.entity.enumeration.Method> method = createEnum("method", com.app.treen.products.entity.enumeration.Method.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final NumberPath<Long> point = createNumber("point", Long.class);

    public final EnumPath<com.app.treen.products.entity.enumeration.Size> size = createEnum("size", com.app.treen.products.entity.enumeration.Size.class);

    public final EnumPath<com.app.treen.products.entity.enumeration.Status> transactionStatus = createEnum("transactionStatus", com.app.treen.products.entity.enumeration.Status.class);

    public final EnumPath<com.app.treen.products.entity.enumeration.UsedRank> usedRank = createEnum("usedRank", com.app.treen.products.entity.enumeration.UsedRank.class);

    public final StringPath usedTerm = createString("usedTerm");

    public final com.app.treen.user.entity.QUser user;

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QTransProduct(String variable) {
        this(TransProduct.class, forVariable(variable), INITS);
    }

    public QTransProduct(Path<? extends TransProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTransProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTransProduct(PathMetadata metadata, PathInits inits) {
        this(TransProduct.class, metadata, inits);
    }

    public QTransProduct(Class<? extends TransProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.user = inits.isInitialized("user") ? new com.app.treen.user.entity.QUser(forProperty("user")) : null;
    }

}

