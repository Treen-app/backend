package com.app.treen.chat_room.controller;

import com.app.treen.chat_room.dto.request.ChatRoomRequestDto;
import com.app.treen.chat_room.dto.request.MessageRequestDto;
import com.app.treen.chat_room.dto.response.*;
import com.app.treen.chat_room.service.ChatRoomService;
import com.app.treen.user.service.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final SimpMessageSendingOperations template;
    private final ChatRoomService chatRoomService;

    // 채팅방 목록 조회
    @GetMapping("/auth/chat-list")
    public ResponseEntity<ChatRoomsResponse> getChatRooms(@AuthenticationPrincipal CustomUserDetails userDetails) {

        ChatRoomsResponse chatRoomsResponse = ChatRoomsResponse.builder()
                .chatRooms(chatRoomService.getChatRooms(userDetails.getUser().getId()))
                .build();

        return ResponseEntity.ok(chatRoomsResponse);
    }

    // 채팅방 조회
    @GetMapping("auth/{chatRoomId}")
    public Mono<ResponseEntity<ChatRoomDetailResponse>> getChatRoomDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                 @PathVariable Long chatRoomId) {

        return chatRoomService.getChatRoomDetail(userDetails.getUser().getId(), chatRoomId)
                .map(ResponseEntity::ok);
    }

    // 채팅방 생성
    @PostMapping("auth/create")
    public ResponseEntity<ChatRoomCreateResponse> createChatRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                              @Valid @RequestBody ChatRoomRequestDto dto) {
        Long chatRoomId = chatRoomService.saveChatRoom(userDetails.getUser().getId(),
                dto.getSellerId(), dto.getProductId());
        return ResponseEntity.ok(ChatRoomCreateResponse.builder()
                .chatRoomId(chatRoomId)
                .build());
    }

    // 이전 채팅 메시지들 조회
    @GetMapping("/find/chat/list/{id}")
    public Mono<ResponseEntity<List<MessageResponseDto>>> findMessages(@PathVariable("id") Long id) {
        Flux<MessageResponseDto> response = chatRoomService.findChatMessages(id);
        return response.collectList().map(ResponseEntity::ok);
    }

    // ======== 테스트 API 작성 ==========

    // 채팅방 조회
    @GetMapping("test/{chatRoomId}")
    public Mono<ResponseEntity<ChatRoomDetailResponse>> getChatRoomDetailTest(
                                                                          @PathVariable Long chatRoomId) {

        return chatRoomService.getChatRoomDetail(1L, chatRoomId)
                .map(ResponseEntity::ok);
    }

    @GetMapping("test/create")
    public ResponseEntity<ChatRoomCreateResponse> createChatRoomTest(
                                                                 @Valid @RequestBody ChatRoomRequestDto dto) {
        Long chatRoomId = chatRoomService.saveChatRoom(1L,
                dto.getSellerId(), dto.getProductId());
        return ResponseEntity.ok(ChatRoomCreateResponse.builder()
                .chatRoomId(chatRoomId)
                .build());
    }

    @GetMapping("test/find/chat/list/{id}")
    public Mono<ResponseEntity<List<MessageResponseDto>>> findMessagesTest(@PathVariable("id") Long id) {
        Flux<MessageResponseDto> response = chatRoomService.findChatMessages(id);
        return response.collectList().map(ResponseEntity::ok);
    }

    // 메시지 송신 및 수신
//    @MessageMapping("/message")
//    public Mono<ResponseEntity<Void>> receiveMessage(@RequestBody MessageRequestDto chat) {
//        return chatRoomService.saveChatMessage(chat).doOnNext(message -> {
//            // 메시지를 해당 채팅방 구독자들에게 전송
//            template.convertAndSend("/sub/chatroom/" + chat.getRoomId(),
//                    MessageResponseDto.of(message));
//
//        }).thenReturn(ResponseEntity.ok().build());
//    }

}
