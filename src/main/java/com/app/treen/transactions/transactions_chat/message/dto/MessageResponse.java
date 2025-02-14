package com.app.treen.transactions.transactions_chat.message.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {

    private Long roomId;

    private String type;

    private String content;
}
