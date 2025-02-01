package com.app.treen.trade.trade_chat.message.document;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "trade_chatting_message") // 실제 몽고 DB 컬렉션 이름
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeMessage {
    @Id
    private ObjectId id;
    private Long roomId;
    private String type; // type == text -> massage 저장, type == image -> url 저장

    @Column(length = 600)
    private String content;
    private Long writerId;
    //private LocalDateTime createdDate;
    private Boolean isRead;

    public TradeMessage(Long roomId, String type, String content, Long writerId) {
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
