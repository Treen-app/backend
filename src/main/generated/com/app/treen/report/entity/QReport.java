package com.app.treen.report.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReport is a Querydsl query type for Report
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReport extends EntityPathBase<Report> {

    private static final long serialVersionUID = -688148707L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReport report = new QReport("report");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath otherReason = createString("otherReason");

    public final EnumPath<ReportReason> reason = createEnum("reason", ReportReason.class);

    public final com.app.treen.products.entity.QTradeProduct reportedPost;

    public final com.app.treen.user.entity.QUser reportedUser;

    public final com.app.treen.user.entity.QUser reporter;

    public QReport(String variable) {
        this(Report.class, forVariable(variable), INITS);
    }

    public QReport(Path<? extends Report> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReport(PathMetadata metadata, PathInits inits) {
        this(Report.class, metadata, inits);
    }

    public QReport(Class<? extends Report> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reportedPost = inits.isInitialized("reportedPost") ? new com.app.treen.products.entity.QTradeProduct(forProperty("reportedPost"), inits.get("reportedPost")) : null;
        this.reportedUser = inits.isInitialized("reportedUser") ? new com.app.treen.user.entity.QUser(forProperty("reportedUser")) : null;
        this.reporter = inits.isInitialized("reporter") ? new com.app.treen.user.entity.QUser(forProperty("reporter")) : null;
    }

}

