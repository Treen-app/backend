package com.app.treen.chatting.dto.request;

import com.app.treen.chatting.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomRequestDto {

    private String title;

    @Builder
    public ChatRoomRequestDto(String title) {
        this.title = title;
    }

    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .title(title)
                .build();
    }
}
