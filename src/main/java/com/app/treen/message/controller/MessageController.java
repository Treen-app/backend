package com.app.treen.message.controller;

import com.app.treen.chat_room.dto.request.MessageRequestDto;
import com.app.treen.chat_room.dto.response.MessageResponseDto;
import com.app.treen.message.dto.MessageRequest;
import com.app.treen.message.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final SimpMessagingTemplate template;

    // 메시지 송신 및 수신
    @MessageMapping("/send")
    public Mono<ResponseEntity<Void>> receiveMessage(@Valid MessageRequest messageRequest) {
        return messageService.saveMessage(messageRequest).flatMap(message -> {
            // 메시지를 해당 채팅방 구독자들에게 전송
            template.convertAndSend("/sub/chatroom/" + messageRequest.getRoomId(),
                    MessageResponseDto.of(message));
            return Mono.just(ResponseEntity.ok().build());
        });
    }

    // 이미지 송신 및 수신
}
