package com.app.treen.products.dto.request;


import com.app.treen.products.entity.TradePImg;
import com.app.treen.products.entity.TradeProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ImgRequestDto {
    private List<String> imageUrls;

    public ImgRequestDto(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<TradePImg> toImageEntities(TradeProduct tradeProduct) {
        List<TradePImg> images = new ArrayList<>();
        for (int i = 0; i < imageUrls.size(); i++) {
            images.add(TradePImg.builder()
                    .tradeProduct(tradeProduct)
                    .imgUrl(imageUrls.get(i))
                    .sortOrder(i)
                    .isMain(i == 0) // 첫 번째 이미지를 대표 이미지로 설정
                    .build());
        }
        return images;
    }
}