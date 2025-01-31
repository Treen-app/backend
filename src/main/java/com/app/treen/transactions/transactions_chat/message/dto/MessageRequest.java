package com.app.treen.transactions.transactions_chat.message.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRequest {

    @NotNull(message = "보내는 사람의 고유 번호는 필수입니다.")
    private Long senderId;

    @NotNull(message = "채팅방 고유 번호는 필수입니다.")
    private Long roomId;

    @NotBlank(message = "채팅 메세지를 입력해주세요.")
    private String content;
}

