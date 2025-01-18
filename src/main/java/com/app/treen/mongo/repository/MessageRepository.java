package com.app.treen.mongo.repository;

import com.app.treen.chat_room.entity.ChatRoom;
import com.app.treen.message.document.Message;
import com.app.treen.user.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.Optional;

public interface MessageRepository extends ReactiveMongoRepository<Message, String> {
    Flux<Message> findAllByRoomId(Long roomId);
    Flux<Message> findAllByRoomIdOrderByIdAsc(Long roomId);

    Integer countByRoomIdAndIsReadIsFalseAndWriterIdNot(Long roomId, Long memberId);

    Optional<Message> findFirstByRoomIdOrderByIdDesc(Long roomId);
}