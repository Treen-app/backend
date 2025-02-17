package com.app.treen.style.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStyleReport is a Querydsl query type for StyleReport
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStyleReport extends EntityPathBase<StyleReport> {

    private static final long serialVersionUID = -1377011569L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStyleReport styleReport = new QStyleReport("styleReport");

    public final com.app.treen.QBaseTimeEntity _super = new com.app.treen.QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final QStyle style;

    public QStyleReport(String variable) {
        this(StyleReport.class, forVariable(variable), INITS);
    }

    public QStyleReport(Path<? extends StyleReport> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStyleReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStyleReport(PathMetadata metadata, PathInits inits) {
        this(StyleReport.class, metadata, inits);
    }

    public QStyleReport(Class<? extends StyleReport> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.style = inits.isInitialized("style") ? new QStyle(forProperty("style"), inits.get("style")) : null;
    }

}

