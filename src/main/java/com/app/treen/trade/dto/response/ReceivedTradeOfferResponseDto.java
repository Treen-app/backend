package com.app.treen.trade.dto.response;

import com.app.treen.products.entity.TradeProduct;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceivedTradeOfferResponseDto { // 받은 요청목록 조회
    private Long tradeOfferId;
    private LocalDateTime createdDate;
    private String myProduct;
    private ReceivedProductDto receivedProduct;

    @Data
    @Builder
    public static class ReceivedProductDto {
        private Long receivedProductId; // 교환 신청 상품 ID
        private String name; // 상품명
        private String size;
        private String category;
        private String usedTerm;
        private String tradeType;
        private String buyerName;

        public static ReceivedProductDto from(TradeProduct tradeProduct) {
            return ReceivedProductDto.builder()
                    .receivedProductId(tradeProduct.getId())
                    .name(tradeProduct.getName())
                    .size(tradeProduct.getSize().toString())
                    .category(tradeProduct.getCategory().toString())
                    .usedTerm(tradeProduct.getUsedTerm())
                    .tradeType(tradeProduct.getTradeType().toString())
                    .buyerName(tradeProduct.getUser().getUserName())
                    .build();
        }
    }
}
