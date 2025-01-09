package com.app.treen.chat_room.repository;

import com.app.treen.chat_room.document.ChatMessage;
import com.app.treen.chat_room.entity.ChatRoom;
import com.app.treen.user.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.Optional;

public interface ChatMessageRepository extends ReactiveMongoRepository<ChatMessage, String> {
    Flux<ChatMessage> findAllByRoomId(Long roomId);
    Flux<ChatMessage> findAllByRoomIdOrderByIdAsc(Long roomId);

    Integer countByChatRoomAndIsReadIsFalseAndSenderNot(ChatRoom chatRoom, User member);

    Optional<ChatMessage> findFirstByChatRoomOrderByIdDesc(ChatRoom chatRoom);
}