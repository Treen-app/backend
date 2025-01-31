package com.app.treen.transactions.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionsSaveRequestDto {

    private Long transChatRoomId;
    private LocalDateTime transDate;
    private boolean isDirect;
    private String place;
    private String deliveryAddress;
    private String deliveryRequest;
    private String deliveryFeeAccount;

}
