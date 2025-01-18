package com.app.treen.message.dto;

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
