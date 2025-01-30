package com.app.treen.trade.dto.response;

import com.app.treen.products.entity.TradeProduct;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeOfferResponseDto {

    private Long tradeOfferId;
    private LocalDateTime createdDate;
    private Long salesProductId; // 판매상품ID
    private String salesProductName; // 판매상품명
    private OfferedProductDto offeredProduct;

    @Data
    @Builder
    public static class OfferedProductDto {
        private Long offeredProductId; // 교환 신청 상품 ID
        private String name; // 상품명
        private String size;
        private String category;
        private String tradeType;
        private String userName;

        public static OfferedProductDto from(TradeProduct tradeProduct) {
            return OfferedProductDto.builder()
                    .offeredProductId(tradeProduct.getId())
                    .name(tradeProduct.getName())
                    .size(tradeProduct.getSize().toString())
                    .category(tradeProduct.getCategory().toString())
                    .tradeType(tradeProduct.getTradeType().toString())
                    .userName(tradeProduct.getUser().getUserName())
                    .build();
        }
    }
}
