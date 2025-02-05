package com.app.treen.kafka.dto;

import com.app.treen.transactions.transactions_chat.message.document.Message;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrokerMessageDto implements Serializable {

    private String id; // 메시지 고유 ID
    private Long roomId;
    private long sendTime;
    private String type;
    private String content;
    private Long writerId;
    private Boolean isRead;

    // 메시지 전송시간, 보낸 사람, 읽음 여부 동시에 설정하는 메서드
    public void setSendTimeAndSender(LocalDateTime sendTime, Long writerId, Boolean isRead) {
        // LocalDateTime을 Asia/Seoul 타임존 기준으로 Epoch 밀리초로 변환하여 저장
        this.sendTime = sendTime.atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
        this.writerId = writerId;
        this.isRead = isRead;
    }

    // 메시지 ID를 설정하는 메서드
    public void setMessageId(String id) {
        this.id = id;
    }

    // BrokerMessageDto를 Chatting 엔티티로 변환하는 메서드
    public Message toEntity() {
        return Message.builder()
                .writerId(writerId)
                .roomId(roomId)
                .type(type)
                .content(content)
                // Epoch 밀리초로 저장된 sendTime을 LocalDateTime으로 변환하여 저장
                .sendTime(Instant.ofEpochMilli(sendTime).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime())
                .isRead(isRead)
                .build();
    }

    /**
     * @Id
     *     private ObjectId id;
     *     private Long roomId;
     *     private String type; // type == text -> massage 저장, type == image -> url 저장
     *     @Column(length = 600)
     *     private String content;
     *     private Long writerId;
     *     private Boolean isRead;
     */


    /**
     * @Id
     *     private String id;
     *     private Integer chatRoomNo;
     *     private String senderName;
     *     private String content;
     *     private LocalDateTime sendDate;
     *     private long readCount;
     */
}
