package com.app.treen.message.controller;

import com.app.treen.chat_room.dto.request.MessageRequestDto;
import com.app.treen.chat_room.dto.response.MessageResponseDto;
import com.app.treen.message.dto.MessageRequest;
import com.app.treen.message.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
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
    //private final SimpMessagingTemplate template;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    // 메시지 송신 및 수신
    // 클라이언트가 메시지를 전송하면, 해당 메서드 실행
    @MessageMapping("/send")
    public Mono<ResponseEntity<Void>> receiveMessage(@Valid MessageRequest messageRequest) {
        // 메시지 저장 로직
        return messageService.saveMessage(messageRequest).flatMap(savedMessage -> {
            try {
                // 메시지를 Redis 채널로 퍼블리시
                String publishMessage = new ObjectMapper().writeValueAsString(messageRequest);
                redisTemplate.convertAndSend("chatroom/" + messageRequest.getRoomId(), publishMessage);

                log.info("Message published to Redis channel: {}", "chatroom/" + messageRequest.getRoomId());

                // 응답 반환
                return Mono.just(ResponseEntity.ok().build());
            } catch (Exception e) {
                log.error("Failed to publish message to Redis", e);
                return Mono.error(e);
            }
        });
    }

    // 이미지 송신 및 수신
}
