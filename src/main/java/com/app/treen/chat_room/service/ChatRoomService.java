package com.app.treen.chat_room.service;

import com.app.treen.chat_room.document.ChatMessage;
import com.app.treen.common.response.code.status.ErrorStatus;
import com.app.treen.chat_room.dto.request.MessageRequestDto;
import com.app.treen.chat_room.dto.response.ChatRoomDetailResponse;
import com.app.treen.chat_room.dto.response.MessageResponseDto;
import com.app.treen.chat_room.dto.response.ChatRoomResponseDto;
import com.app.treen.chat_room.entity.ChatRoom;
import com.app.treen.chat_room.repository.ChatMessageRepository;
import com.app.treen.chat_room.repository.ChatRoomRepository;
import com.app.treen.products.entity.TransProductRepository;
import com.app.treen.user.entity.User;
import com.app.treen.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final TransProductRepository transProductRepository;
    private final UserRepository userRepository;

    // 채팅방 생성
    @Transactional
    public long saveChatRoom(Long loginMemberId, Long sellerId, Long productId) {

        Optional<ChatRoom> chatRoomOptional =
                chatRoomRepository.findBySellerIdAndBuyerIdAndTransProductId(loginMemberId, sellerId, productId);

        // 기존 채팅방이 존재하는 경우 해당 id 반환
        if (chatRoomOptional.isPresent()) {
            return chatRoomOptional.get().getId();
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .buyer(userRepository.findById(loginMemberId)
                        .orElseThrow())
                .seller(userRepository.findById(sellerId)
                        .orElseThrow())
                .transProduct(transProductRepository.findById(productId)
                        .orElseThrow())
                .build();

        return chatRoomRepository.save(chatRoom).getId();
    }

    // 채팅방 목록 조회
    public List<ChatRoomResponseDto> getChatRooms(Long loginMemberId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllBySellerIdOrBuyerId(loginMemberId, loginMemberId);
        User member = userRepository.findById(loginMemberId)
                .orElseThrow(() -> new RuntimeException(ErrorStatus.USER_NOT_FOUND.getMessage()));
        return chatRooms.stream().map(chatRoom -> ChatRoomResponseDto.builder()
                .chatRoomId(chatRoom.getId())
                        .unreadCount(getUnreadCount(chatRoom, member))
                        //.otherMember(getOtherMemberDto(chatRoom.getOtherMember(loginMemberId)))
                .lastMessage(getLastMessageDto(chatRoom))
                .build())
                .collect(Collectors.toList());
    }

    private ChatRoomResponseDto.MessageDto getLastMessageDto(ChatRoom chatRoom) {
        return chatMessageRepository.findFirstByChatRoomOrderByIdDesc(chatRoom)
                .map(ChatRoomResponseDto.MessageDto::from)
                .orElse(null);
    }

    // 채팅 메시지 조회
    public Flux<MessageResponseDto> findChatMessages(Long id) {
        Flux<ChatMessage> chatMessages = chatMessageRepository.findAllByRoomId(id);
        return chatMessages.map(MessageResponseDto::of);
    }

    @Transactional
    public Mono<ChatMessage> saveChatMessage(MessageRequestDto chat) {
        return chatMessageRepository.save(new ChatMessage(chat.getRoomId(), chat.getType(), chat.getContent(), chat.getWriterId(), LocalDateTime.now()));
    }

    public Mono<ChatRoomDetailResponse> getChatRoomDetail(Long loginMemberId, Long chatRoomId) {

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException(ErrorStatus.USER_NOT_FOUND.getMessage()));

        // Flux로 메세지를 비동기적으로 가져옴
        Flux<ChatMessage> messages = chatMessageRepository.findAllByRoomIdOrderByIdAsc(chatRoomId);

        return messages
                .doOnNext(message -> message.read(loginMemberId))
                .collectList() // Flux<ChatMessage>를 List로 변환
                .map(messagesList -> ChatRoomDetailResponse.builder()
                        .chatRoomId(chatRoomId)
                        .seller(ChatRoomDetailResponse.MemberDto.from(chatRoom.getSeller()))
                        .buyer(ChatRoomDetailResponse.MemberDto.from(chatRoom.getBuyer()))
                        .messages(messagesList.stream()
                                .map(ChatRoomDetailResponse.MessageDto::from)
                                .collect(Collectors.toList()))
                        .build());
    }

//    private ChatRoomResponseDto.MemberDto getOtherMemberDto(User otherMember) {
//        return ChatRoomResponseDto.MemberDto.of(otherMember, fileManager.getFullPath(otherMember.getImage()));
//    }

    private Integer getUnreadCount(ChatRoom chatRoom, User member) {
        return chatMessageRepository.countByChatRoomAndIsReadIsFalseAndSenderNot(chatRoom, member);
    }

    @Transactional
    public void leaveChatRoom(Long chatRoomId) {

        if (chatRoomRepository.existsById(chatRoomId)) {
            chatRoomRepository.deleteById(chatRoomId);
        }
    }

    // 채팅방 마지막 메시지 조회
//    private ChatRoomResponse.MessageDto getLastMessageDto(ChatRoom chatRoom) {
//        return messageRepository.findFirstByChatRoomOrderByIdDesc(chatRoom)
//                .map(ChatRoomResponse.MessageDto::from)
//                .orElse(null);
//
//    }
}
