package com.app.treen.trade.dto.request;

import com.app.treen.products.entity.TradeProduct;
import com.app.treen.trade.dto.response.TradeOfferResponseDto;
import lombok.*;

import java.time.LocalDateTime;

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
