package com.app.treen.chatting.service;

import com.app.treen.chatting.document.ChatMessage;
import com.app.treen.chatting.dto.request.ChatRoomRequestDto;
import com.app.treen.chatting.dto.request.MessageRequestDto;
import com.app.treen.chatting.dto.response.MessageResponseDto;
import com.app.treen.chatting.dto.response.ChatRoomResponseDto;
import com.app.treen.chatting.entity.ChatRoom;
import com.app.treen.chatting.repository.ChatMessageRepository;
import com.app.treen.chatting.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    // 채팅방 생성
    @Transactional
    public ChatRoomResponseDto saveChatRoom(ChatRoomRequestDto requestDto) {
        ChatRoom savedChatRoom = requestDto.toEntity();
        return ChatRoomResponseDto.from(savedChatRoom);
    }

    // 채팅방 목록 조회
    public List<ChatRoomResponseDto> findChatRoomList(Long sellerId, Long buyerId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllBySellerIdOrBuyerId(sellerId, buyerId);

        return chatRooms.stream().map(chatRoom -> ChatRoomResponseDto.builder()
                .chatRoomId(chatRoom.getId())
                //.lastMessage(getLastMessage(chatRoom))
                .build())
                .collect(Collectors.toList());

    }

    // 채팅 메시지 조회
    public Flux<MessageResponseDto> findChatMessages(Long id) {
        Flux<ChatMessage> chatMessages = chatMessageRepository.findAllByRoomId(id);
        return chatMessages.map(MessageResponseDto::of);
    }

    @Transactional
    public Mono<ChatMessage> saveChatMessage(MessageRequestDto chat) {
        return chatMessageRepository.save(new ChatMessage(chat.getRoomId(), chat.getContent(), chat.getWriterId(), new Date()));
    }

    // 채팅방 마지막 메시지 조회
//    private ChatRoomResponse.MessageDto getLastMessageDto(ChatRoom chatRoom) {
//        return messageRepository.findFirstByChatRoomOrderByIdDesc(chatRoom)
//                .map(ChatRoomResponse.MessageDto::from)
//                .orElse(null);
//
//    }
}
