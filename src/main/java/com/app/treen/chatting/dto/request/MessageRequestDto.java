package com.app.treen.chatting.dto.request;

import com.app.treen.chatting.document.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestDto {
    private String id; // ObjectId는 String 으로 변환하여 사용
    private Long roomId;
    private String content;
    private Long writerId;
    //private Date createdDate;

    public static MessageRequestDto of(ChatMessage message) {
        return MessageRequestDto.builder()
                .id(message.getId().toHexString())
                .roomId(message.getRoomId())
                .content(message.getContent())
                .writerId(message.getWriterId())
                //.createdDate(message.getCreatedDate())
                .build();
    }

}
