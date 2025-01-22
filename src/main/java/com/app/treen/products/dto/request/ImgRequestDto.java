package com.app.treen.products.dto.request;


import com.app.treen.products.entity.TradePImg;
import com.app.treen.products.entity.TradeProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ImgRequestDto {

    public List<TradePImg> toImageEntities(TradeProduct tradeProduct , List<String> imageUrls) {
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