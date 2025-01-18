package com.app.treen.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1599087093L;

    public static final QUser user = new QUser("user");

<<<<<<< HEAD
=======
    public final DateTimePath<java.time.LocalDateTime> birthDate = createDateTime("birthDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> gender = createNumber("gender", Integer.class);

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

>>>>>>> chatting
    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath loginId = createString("loginId");

<<<<<<< HEAD
=======
    public final StringPath nickname = createString("nickname");

>>>>>>> chatting
    public final StringPath password = createString("password");

    public final StringPath phoneNum = createString("phoneNum");

    public final NumberPath<Long> point = createNumber("point", Long.class);

    public final StringPath profileImgUrl = createString("profileImgUrl");

    public final ListPath<RoleType, EnumPath<RoleType>> roles = this.<RoleType, EnumPath<RoleType>>createList("roles", RoleType.class, EnumPath.class, PathInits.DIRECT2);

    public final EnumPath<UserStatus> status = createEnum("status", UserStatus.class);

    public final StringPath userName = createString("userName");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

