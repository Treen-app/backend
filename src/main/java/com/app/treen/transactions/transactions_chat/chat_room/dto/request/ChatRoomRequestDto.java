package com.app.treen.transactions.transactions_chat.chat_room.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomRequestDto {

    @NotNull(message = "판매자 고유 번호는 필수입니다.")
    private Long sellerId;

    @NotNull(message = "상품 고유 번호는 필수입니다.")
    private Long productId;
}
