package com.app.treen.jpa.repository.transactions;

import com.app.treen.transactions.transactions_chat.chat_room.entity.ChatRoom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findBySellerIdAndBuyerIdAndTransProductId(Long sellerId, Long buyerId, Long transProductId);

    @EntityGraph(attributePaths = {"transProduct", "seller", "buyer"})
    List<ChatRoom> findAllBySellerIdOrBuyerId(Long sellerId, Long buyerId);
}
