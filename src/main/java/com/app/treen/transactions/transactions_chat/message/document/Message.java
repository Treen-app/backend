package com.app.treen.transactions.transactions_chat.message.document;

import com.app.treen.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "chatting_message") // 실제 몽고 DB 컬렉션 이름
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message extends BaseTimeEntity {
    @Id
    private ObjectId id;
    private Long roomId;
    private String type; // type == text -> massage 저장, type == image -> url 저장
    @Column(length = 600)
    private String content;
    private Long writerId;
    private Boolean isRead;
    private LocalDateTime sendTime;

    public Message(Long roomId, String type, String content, Long writerId) {
        this.roomId = roomId;
        this.type = type;
        this.content = content;
        this.writerId = writerId;
    }

    public void read(Long writerId) {
        boolean isMyMessage = writerId.equals(writerId);

        if (!isMyMessage) {
            isRead = true;
        }
    }

}
