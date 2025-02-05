package com.app.treen.transactions.transactions_chat.message.service;

import com.app.treen.common.redis.chat_room_connection.repository.ChatRoomConnectionRepository;
import com.app.treen.transactions.transactions_chat.chat_room.entity.ChatRoom;
import com.app.treen.jpa.repository.transactions.ChatRoomRepository;
import com.app.treen.common.response.code.status.ErrorStatus;
import com.app.treen.transactions.transactions_chat.message.document.Message;
import com.app.treen.transactions.transactions_chat.message.dto.MessageRequest;
import com.app.treen.mongo.repository.MessageRepository;
import com.app.treen.mongo.repository.MessageType;
import com.app.treen.user.entity.User;
import com.app.treen.jpa.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomConnectionRepository chatRoomConnectionRepository;
    //private final FileManager fileManager;
    private final ApplicationEventPublisher eventPublisher;

    public Mono<Message> saveMessage(MessageRequest messageRequest) {

        ChatRoom chatRoom = getChatRoomOrThrow(messageRequest.getRoomId());
        User member = getUserOrThrow(messageRequest.getSenderId());
        int connectionCount = (int) chatRoomConnectionRepository.getUsersCountInRoom(messageRequest.getRoomId());

        Message message = Message.builder()
                .writerId(messageRequest.getSenderId())
                .roomId(messageRequest.getRoomId())
                .type(MessageType.TEXT.name())
                .content(messageRequest.getContent())
                .isRead(isOtherConnecting(connectionCount)) // 상대방이 접속 상태면 읽음 처리
                .build();

        return messageRepository.save(message);

//        if (!isOtherConnecting(connectionCount)) {
//            eventPublisher.publishEvent(new MessageEvent(chatRoom, message, NotificationCode.CHATTING));
//        }

        // -> 참고 수정
//        return MessageResponse.builder()
//                .roomId(messageRequest.getRoomId())
//                .type(MessageType.TEXT.name())
//                .content(messageRequest.getContent())
//                .build();
    }

    // 채팅방 접속자 수 확인
    private boolean isOtherConnecting(int connectionCount) {

        return connectionCount == 2; // 발송자와 상대방이 접속한 경우
    }

    private User getUserOrThrow(Long memberId) {
        return userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException(ErrorStatus.USER_NOT_FOUND.getMessage()));
    }

    private ChatRoom getChatRoomOrThrow(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException(ErrorStatus.NOT_FOUND_CHAT_ROOM.getMessage()));
    }

}
