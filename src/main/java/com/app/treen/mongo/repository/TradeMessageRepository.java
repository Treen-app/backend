package com.app.treen.mongo.repository;

import com.app.treen.transactions.transactions_chat.message.document.Message;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TradeMessageRepository extends ReactiveMongoRepository<Message, String> {


}
