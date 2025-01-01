package com.app.treen.chatting.dto.response;

import com.app.treen.chatting.entity.ChatRoom;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomResponseDto {

    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private boolean isReserved;

    public static ChatRoomResponseDto from(ChatRoom chatRoom) {
        return ChatRoomResponseDto.builder()
                .id(chatRoom.getId())
                .title(chatRoom.getTitle())
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }

}
