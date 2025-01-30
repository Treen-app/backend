package com.app.treen.jpa.repository.transactions;

import com.app.treen.chat_room.entity.ChatRoom;
import com.app.treen.transactions.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    Optional<Transactions> findByTransChatRoom(ChatRoom transChatRoom);

}
