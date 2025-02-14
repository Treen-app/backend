package com.app.treen.trade.dto.response;

import com.app.treen.products.entity.TradeProduct;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SentTradeOfferResponseDto { // 보낸 요청목록 조회

    private Long tradeOfferId;
    private LocalDateTime createdDate;
    private String myProduct;
    private SalesProductDto salesProduct;

    @Data
    @Builder
    public static class SalesProductDto {
        private Long salesProductId;
        private String name;
        private String size;
        private String usedTerm;
        private String tradeType;
        private String sellerName;

        public static SalesProductDto from(TradeProduct tradeProduct) {
            return SalesProductDto.builder()
                    .salesProductId(tradeProduct.getId())
                    .name(tradeProduct.getName())
                    .size(tradeProduct.getSize().toString())
                    .usedTerm(tradeProduct.getUsedTerm())
                    .tradeType(tradeProduct.getTradeType().toString())
                    .sellerName(tradeProduct.getUser().getUserName())
                    .build();
        }
    }

//    @Data
//    @Builder
//    public static class OfferedProductDto {
//        private Long offeredProductId; // 교환 신청 상품 ID
//        private String name; // 상품명
//        private String size;
//        private String category;
//        private String tradeType;
//        private String userName;
//
//        public static OfferedProductDto from(TradeProduct tradeProduct) {
//            return OfferedProductDto.builder()
//                    .offeredProductId(tradeProduct.getId())
//                    .name(tradeProduct.getName())
//                    .size(tradeProduct.getSize().toString())
//                    .category(tradeProduct.getCategory().toString())
//                    .tradeType(tradeProduct.getTradeType().toString())
//                    .userName(tradeProduct.getUser().getUserName())
//                    .build();
//        }
//    }
}
