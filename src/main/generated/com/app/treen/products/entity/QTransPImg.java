package com.app.treen.products.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTransPImg is a Querydsl query type for TransPImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTransPImg extends EntityPathBase<TransPImg> {

    private static final long serialVersionUID = -811025086L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTransPImg transPImg = new QTransPImg("transPImg");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgUrl = createString("imgUrl");

    public final BooleanPath isMain = createBoolean("isMain");

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    public final QTransProduct transProduct;

    public QTransPImg(String variable) {
        this(TransPImg.class, forVariable(variable), INITS);
    }

    public QTransPImg(Path<? extends TransPImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTransPImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTransPImg(PathMetadata metadata, PathInits inits) {
        this(TransPImg.class, metadata, inits);
    }

    public QTransPImg(Class<? extends TransPImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.transProduct = inits.isInitialized("transProduct") ? new QTransProduct(forProperty("transProduct"), inits.get("transProduct")) : null;
    }

}

