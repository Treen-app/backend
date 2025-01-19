//package com.app.treen.chat_room.controller;
//
//import com.app.treen.chat_room.dto.request.ChatRoomRequestDto;
//import com.app.treen.chat_room.dto.request.MessageRequestDto;
//import com.app.treen.chat_room.dto.response.*;
//import com.app.treen.chat_room.service.ChatRoomService;
//import com.app.treen.common.jwt.service.dto.CustomUserDetails;
//import com.app.treen.common.response.ResponseEntity;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//public class ChatRoomController {
//
//    private final SimpMessageSendingOperations template;
//    private final ChatRoomService chatRoomService;
//
//    // 채팅리스트 반환
////    @GetMapping("/chat/{id}")
////    public ResponseEntity<List<ChatMessageResponseDto>> getChatMessages(@PathVariable Long id) {
////        // 임시로 리스트 형식으로 구현, 실제로는 DB 접근 필요
////        ChatMessageResponseDto test = new ChatMessageResponseDto(1L, "test", "test");
////        return ResponseEntity.ok().body(List.of(test));
////    }
//
//    // 메시지 송신 및 수신, /pub가 생략된 모습. 클라이언트 단에서는 /pub/message 로 요청
////    @MessageMapping("/message")
////    public ResponseEntity<Void> receiveMessage(@RequestBody MessageResponseDto chat) {
////        // 메시지를 해당 채팅방 구독자들에게 전송
////        template.convertAndSend("/sub/chatroom/1", chat);
////        return ResponseEntity.ok().build();
////    }
//
//    // 채팅방 목록 조회
//    @GetMapping("/auth/chat-list")
//    public ResponseEntity<ChatRoomsResponse> getChatRooms(@AuthenticationPrincipal CustomUserDetails userDetails) {
//
//        ChatRoomsResponse chatRoomsResponse = ChatRoomsResponse.builder()
//                .chatRooms(chatRoomService.getChatRooms(userDetails.getMember().getId()))
//                .build();
//
//        return ResponseEntity.onSuccess(chatRoomsResponse);
//    }
//
//    // 채팅방 조회
//    @GetMapping("auth/{chatRoomId}")
//    public Mono<ResponseEntity<ChatRoomDetailResponse>> getChatRoomDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
//                                                                 @PathVariable Long chatRoomId) {
//
//        return chatRoomService.getChatRoomDetail(userDetails.getMember().getId(), chatRoomId)
//                .map(ResponseEntity::onSuccess);
//    }
//
//    // 채팅방 생성
//    @PostMapping("auth/create")
//    public ResponseEntity<ChatRoomCreateResponse> createChatRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
//                                                              @Valid @RequestBody ChatRoomRequestDto dto) {
//        Long chatRoomId = chatRoomService.saveChatRoom(userDetails.getMember().getId(),
//                dto.getSellerId(), dto.getProductId());
//        return ResponseEntity.onSuccess(ChatRoomCreateResponse.builder()
//                .chatRoomId(chatRoomId)
//                .build());
//    }
//
//    // 이전 채팅 메시지들 조회
//    @GetMapping("/find/chat/list/{id}")
//    public Mono<ResponseEntity<List<MessageResponseDto>>> findMessages(@PathVariable("id") Long id) {
//        Flux<MessageResponseDto> response = chatRoomService.findChatMessages(id);
//        return response.collectList().map(ResponseEntity::onSuccess);
//    }
//
//    // 메시지 송신 및 수신
//    @MessageMapping("/message")
//    public Mono<ResponseEntity<Void>> receiveMessage(@RequestBody MessageRequestDto chat) {
//        return chatRoomService.saveChatMessage(chat).doOnNext(message -> {
//            // 메시지를 해당 채팅방 구독자들에게 전송
//            template.convertAndSend("/sub/chatroom/" + chat.getRoomId(),
//                    MessageResponseDto.of(message));
//
//        }).thenReturn(ResponseEntity.ok().build());
//    }
//
//}
/*
    // 채팅방 목록 조회
    @GetMapping("/auth/chat-list")
    public ResponseEntity<ChatRoomsResponse> getChatRooms(@AuthenticationPrincipal CustomUserDetails userDetails) {

        ChatRoomsResponse chatRoomsResponse = ChatRoomsResponse.builder()
                .chatRooms(chatRoomService.getChatRooms(userDetails.getMember().getId()))
                .build();

        return ResponseEntity.onSuccess(chatRoomsResponse);
    }

    // 채팅방 조회
    @GetMapping("auth/{chatRoomId}")
    public Mono<ResponseEntity<ChatRoomDetailResponse>> getChatRoomDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                 @PathVariable Long chatRoomId) {

        return chatRoomService.getChatRoomDetail(userDetails.getMember().getId(), chatRoomId)
                .map(ResponseEntity::onSuccess);
    }

    // 채팅방 생성
    @PostMapping("auth/create")
    public ResponseEntity<ChatRoomCreateResponse> createChatRoom(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                              @Valid @RequestBody ChatRoomRequestDto dto) {
        Long chatRoomId = chatRoomService.saveChatRoom(userDetails.getMember().getId(),
                dto.getSellerId(), dto.getProductId());
        return ResponseEntity.onSuccess(ChatRoomCreateResponse.builder()
                .chatRoomId(chatRoomId)
                .build());
    }

    // 이전 채팅 메시지들 조회
    @GetMapping("/find/chat/list/{id}")
    public Mono<ResponseEntity<List<MessageResponseDto>>> findMessages(@PathVariable("id") Long id) {
        Flux<MessageResponseDto> response = chatRoomService.findChatMessages(id);
        return response.collectList().map(ResponseEntity::onSuccess);
    }

 */

//    // 메시지 송신 및 수신
//    @MessageMapping("/message")
//    public Mono<ResponseEntity<Void>> receiveMessage(@RequestBody MessageRequestDto chat) {
//        return chatRoomService.saveChatMessage(chat).doOnNext(message -> {
//            // 메시지를 해당 채팅방 구독자들에게 전송
//            template.convertAndSend("/sub/chatroom/" + chat.getRoomId(),
//                    MessageResponseDto.of(message));
//
//        }).thenReturn(ResponseEntity.ok().build());
//    }

