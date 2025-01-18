package com.app.treen.chat_room.dto.response;

import com.app.treen.message.document.Message;
import com.app.treen.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatRoomResponseDto {

    private Long chatRoomId;
    private String title;
    private LocalDateTime createdAt;
    private boolean isReserved;
    private int unreadCount;
    private MemberDto otherMember;
    private MessageDto lastMessage;

    @Data
    @Builder
    public static class MemberDto {
        private Long id;
        private String name;
        private String image;

        public static MemberDto of(User member, String image) {
            return MemberDto.builder()
                    .id(member.getId())
                    .name(member.getUserName())
                    .image(image)
                    .build();
        }
    }

    @Data
    @Builder
    public static class MessageDto {
        private String type;
        private String content;

        @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
        private LocalDateTime sendDate;

        public static MessageDto from(Message message) {
            return MessageDto.builder()
                    .type(message.getType())
                    .content(message.getContent())
                    .sendDate(message.getCreatedDate())
                    .build();
        }
    }

}
