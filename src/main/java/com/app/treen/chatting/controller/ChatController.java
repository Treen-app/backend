package com.app.treen.chatting.controller;

import com.app.treen.chatting.dto.request.ChatRoomRequestDto;
import com.app.treen.chatting.dto.request.MessageRequestDto;
import com.app.treen.chatting.dto.response.MessageResponseDto;
import com.app.treen.chatting.dto.response.ChatRoomResponseDto;
import com.app.treen.chatting.service.ChatService;
import com.app.treen.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations template;
    private final ChatService chatService;

    // 채팅리스트 반환
//    @GetMapping("/chat/{id}")
//    public ResponseEntity<List<ChatMessageResponseDto>> getChatMessages(@PathVariable Long id) {
//        // 임시로 리스트 형식으로 구현, 실제로는 DB 접근 필요
//        ChatMessageResponseDto test = new ChatMessageResponseDto(1L, "test", "test");
//        return ResponseEntity.ok().body(List.of(test));
//    }

    // 메시지 송신 및 수신, /pub가 생략된 모습. 클라이언트 단에서는 /pub/message 로 요청
    @MessageMapping("/message")
    public ResponseEntity<Void> receiveMessage(@RequestBody MessageResponseDto chat) {
        // 메시지를 해당 채팅방 구독자들에게 전송
        template.convertAndSend("/sub/chatroom/1", chat);
        return ResponseEntity.ok().build();
    }

    // 채팅방 생성
    @PostMapping("/create")
    public ApiResponse<ChatRoomResponseDto> createChatRoom(@RequestBody ChatRoomRequestDto dto) {
        return ApiResponse.onSuccess(chatService.saveChatRoom(dto));
    }

    // 이전 채팅 메시지들 조회
    @GetMapping("/find/chat/list/{id}")
    public Mono<ApiResponse<List<MessageResponseDto>>> findMessages(@PathVariable("id") Long id) {
        Flux<MessageResponseDto> response = chatService.findChatMessages(id);
        return response.collectList().map(ApiResponse::onSuccess);
    }

    // 메시지 송신 및 수신
    @MessageMapping("/message")
    public Mono<ResponseEntity<Void>> receiveMessage(@RequestBody MessageRequestDto chat) {
        return chatService.saveChatMessage(chat).doOnNext(message -> {
            // 메시지를 해당 채팅방 구독자들에게 전송
            template.convertAndSend("/sub/chatroom/" + chat.getRoomId(),
                    MessageResponseDto.of(message));

        }).thenReturn(ResponseEntity.ok().build());
    }

}
