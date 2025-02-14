package com.app.treen.transactions.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTransactions is a Querydsl query type for Transactions
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTransactions extends EntityPathBase<Transactions> {

    private static final long serialVersionUID = 1444190239L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTransactions transactions = new QTransactions("transactions");

    public final com.app.treen.QBaseTimeEntity _super = new com.app.treen.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath deliveryAddress = createString("deliveryAddress");

    public final StringPath deliveryFeeAccount = createString("deliveryFeeAccount");

    public final StringPath deliveryRequest = createString("deliveryRequest");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDirect = createBoolean("isDirect");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath place = createString("place");

    public final com.app.treen.transactions.transactions_chat.chat_room.entity.QChatRoom transChatRoom;

    public final DateTimePath<java.time.LocalDateTime> transDate = createDateTime("transDate", java.time.LocalDateTime.class);

    public QTransactions(String variable) {
        this(Transactions.class, forVariable(variable), INITS);
    }

    public QTransactions(Path<? extends Transactions> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTransactions(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTransactions(PathMetadata metadata, PathInits inits) {
        this(Transactions.class, metadata, inits);
    }

    public QTransactions(Class<? extends Transactions> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.transChatRoom = inits.isInitialized("transChatRoom") ? new com.app.treen.transactions.transactions_chat.chat_room.entity.QChatRoom(forProperty("transChatRoom"), inits.get("transChatRoom")) : null;
    }

}

