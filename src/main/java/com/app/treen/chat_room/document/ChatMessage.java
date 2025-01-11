package com.app.treen.chat_room.document;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "chatting_message") // 실제 몽고 DB 컬렉션 이름
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    @Id
    private ObjectId id;
    private Long roomId;
    private String type;

    @Column(length = 600)
    private String content;
    private Long writerId;
    private LocalDateTime createdDate; // type == text -> massage 저장, type == image -> url 저장

    private Boolean isRead;


    public ChatMessage(Long roomId, String type, String content, Long writerId, LocalDateTime createdDate) {
        this.roomId = roomId;
        this.type = type;
        this.content = content;
        this.writerId = writerId;
        this.createdDate = createdDate;
    }

    public void setCreatedDate(long memberId) {
        boolean isMyMessage = writerId.equals(memberId);

        if (!isMyMessage) {
            isRead = true;
        }
    }

    public void read(Long writerId) {
        boolean isMyMessage = writerId.equals(writerId);

        if (!isMyMessage) {
            isRead = true;
        }
    }

}
