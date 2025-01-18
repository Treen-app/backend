package com.app.treen.chat_room.dto.response;

import com.app.treen.message.document.Message;
import com.app.treen.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ChatRoomDetailResponse {

    private Long chatRoomId;
    private List<MessageDto> messages;
    private MemberDto buyer;
    private MemberDto seller;

    @Data
    @Builder
    public static class MemberDto {
        private Long id;
        private String name;

        public static MemberDto from(User user) {
            return MemberDto.builder()
                    .id(user.getId())
                    .name(user.getNickname())
                    .build();
        }
    }

    @Data
    @Builder
    public static class MessageDto {
        private Long senderId;
        private String type;
        private String content;

        @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
        private LocalDateTime sendDate;

        public static MessageDto from(Message message) {
            return MessageDto.builder()
                    .senderId(message.getWriterId())
                    .type(message.getType())
                    .content(message.getContent())
                    .sendDate(message.getCreatedDate())
                    .build();
        }
    }

}
