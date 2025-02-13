package com.app.treen.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 664298597L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final ListPath<ReviewAdvantage, EnumPath<ReviewAdvantage>> advantages = this.<ReviewAdvantage, EnumPath<ReviewAdvantage>>createList("advantages", ReviewAdvantage.class, EnumPath.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<ReviewRating> rating = createEnum("rating", ReviewRating.class);

    public final com.app.treen.user.entity.QUser reviewedUser;

    public final com.app.treen.user.entity.QUser reviewer;

    public final com.app.treen.products.entity.QTradeProduct trade;

    public final com.app.treen.products.entity.QTransProduct transaction;

    public final StringPath transType = createString("transType");

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reviewedUser = inits.isInitialized("reviewedUser") ? new com.app.treen.user.entity.QUser(forProperty("reviewedUser")) : null;
        this.reviewer = inits.isInitialized("reviewer") ? new com.app.treen.user.entity.QUser(forProperty("reviewer")) : null;
        this.trade = inits.isInitialized("trade") ? new com.app.treen.products.entity.QTradeProduct(forProperty("trade"), inits.get("trade")) : null;
        this.transaction = inits.isInitialized("transaction") ? new com.app.treen.products.entity.QTransProduct(forProperty("transaction"), inits.get("transaction")) : null;
    }

}

