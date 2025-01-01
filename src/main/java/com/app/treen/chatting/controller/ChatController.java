package com.app.treen.chatting.controller;

import com.app.treen.chatting.dto.request.ChatRoomRequestDto;
import com.app.treen.chatting.dto.response.ChatMessage;
import com.app.treen.chatting.dto.response.ChatRoomResponseDto;
import com.app.treen.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations template;

    // 채팅리스트 반환
    @GetMapping("/chat/{id}")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable Long id) {
        // 임시로 리스트 형식으로 구현, 실제로는 DB 접근 필요
        ChatMessage test = new ChatMessage(1L, "test", "test");
        return ResponseEntity.ok().body(List.of(test));
    }

    // 메시지 송신 및 수신, /pub가 생략된 모습. 클라이언트 단에서는 /pub/message 로 요청
    @MessageMapping("/message")
    public ResponseEntity<Void> receiveMessage(@RequestBody ChatMessage chat) {
        // 메시지를 해당 채팅방 구독자들에게 전송
        template.convertAndSend("/sub/chatroom/1", chat);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ApiResponse<ChatRoomResponseDto> createChatRoom(@RequestBody ChatRoomRequestDto dto) {
        return ApiResponse.
    }

}
