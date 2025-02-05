package com.app.treen.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserProfile is a Querydsl query type for UserProfile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserProfile extends EntityPathBase<UserProfile> {

    private static final long serialVersionUID = 416182558L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserProfile userProfile = new QUserProfile("userProfile");

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    public final StringPath clothingSize = createString("clothingSize");

    public final NumberPath<Integer> footSize = createNumber("footSize", Integer.class);

    public final StringPath gender = createString("gender");

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final QUser user;

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QUserProfile(String variable) {
        this(UserProfile.class, forVariable(variable), INITS);
    }

    public QUserProfile(Path<? extends UserProfile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserProfile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserProfile(PathMetadata metadata, PathInits inits) {
        this(UserProfile.class, metadata, inits);
    }

    public QUserProfile(Class<? extends UserProfile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

