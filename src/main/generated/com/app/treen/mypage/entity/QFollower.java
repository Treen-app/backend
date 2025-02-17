package com.app.treen.mypage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFollower is a Querydsl query type for Follower
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFollower extends EntityPathBase<Follower> {

    private static final long serialVersionUID = 571087566L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFollower follower = new QFollower("follower");

    public final com.app.treen.user.entity.QUser followerUser;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.app.treen.user.entity.QUser user;

    public QFollower(String variable) {
        this(Follower.class, forVariable(variable), INITS);
    }

    public QFollower(Path<? extends Follower> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFollower(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFollower(PathMetadata metadata, PathInits inits) {
        this(Follower.class, metadata, inits);
    }

    public QFollower(Class<? extends Follower> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.followerUser = inits.isInitialized("followerUser") ? new com.app.treen.user.entity.QUser(forProperty("followerUser")) : null;
        this.user = inits.isInitialized("user") ? new com.app.treen.user.entity.QUser(forProperty("user")) : null;
    }

}

