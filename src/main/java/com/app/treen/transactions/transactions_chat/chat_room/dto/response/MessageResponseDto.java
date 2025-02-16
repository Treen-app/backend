package com.app.treen.transactions.transactions_chat.chat_room.dto.response;

import com.app.treen.transactions.transactions_chat.message.document.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDto {
    private String id; // ObjectId는 String으로 변환하여 사용
    private Long roomId;
    private String content;
    private Long writerId;
    private LocalDateTime createdDate;

    /**
     * ChatMessage 객체를 ChatMessageResponseDto로 변환하는 정적 메서드
     *
     * @param chatMessage ChatMessage 객체
     * @return ChatMessageResponseDto 객체
     */
    public static MessageResponseDto of(Message chatMessage) {
        return MessageResponseDto.builder()
                .id(chatMessage.getId().toHexString()) // ObjectId를 String으로 변환
                .roomId(chatMessage.getRoomId())
                .content(chatMessage.getContent())
                .writerId(chatMessage.getWriterId())
                .createdDate(chatMessage.getCreatedDate())
                .build();
    }
}
