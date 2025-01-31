package com.app.treen.trade.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeOfferSaveDto {

    private Long offeredProductId1;
    private Long offeredProductId2;
    private Long offeredProductId3;
    private Long salesProductId;
}
