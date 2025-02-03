package com.app.treen.common.redis.chat_room_connection.service;

import com.app.treen.common.redis.chat_room_connection.repository.ChatRoomConnectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomConnectionService { // STOMP Handler 클래스에서 사용

    private final ChatRoomConnectionRepository chatRoomConnectionRepository;

    public void connect(Long roomId, Long userId) {

        if (chatRoomConnectionRepository.isUserInRoom(roomId, userId)) {
            return;
        }

        chatRoomConnectionRepository.addUserToRoom(roomId, userId);
    }

    public void disconnect(Long roomId, Long userId) {

        if (chatRoomConnectionRepository.isUserInRoom(roomId, userId)) {
            chatRoomConnectionRepository.removeUserFromRoom(roomId, userId);
        }
    }
}
