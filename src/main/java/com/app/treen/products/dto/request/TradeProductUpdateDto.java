package com.app.treen.products.dto.request;

import com.app.treen.products.entity.Category;
import com.app.treen.products.entity.TradePImg;
import com.app.treen.products.entity.TradeProduct;
import com.app.treen.products.entity.WishCategory;
import com.app.treen.products.entity.enumeration.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class TradeProductUpdateDto {

    private String name;
    private String usedTerm;
    private Size size;
    private String detail;
    private UsedRank usedRank;
    private Method method;
    private Long point;
    private Gender gender;
    private List<String> imageUrls;
    private String wishColor;
    private Size wishSize;

    private List<Long> wishCategoryIds; // 희망 카테고리 ID 목록
    private List<Long> regionIds; // 리전 ID 목록

    // 교환 타입
    private TradeType tradeType;

    // 상품 카테고리
    private Long categoryId;

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls; // 서비스에서 URL을 설정
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

    public List<WishCategory> toWishCategoryEntities(TradeProduct tradeProduct, List<Category> wishCategories) {
        List<WishCategory> wishCategoryEntities = new ArrayList<>();
        for (Category category : wishCategories) {
            wishCategoryEntities.add(WishCategory.builder()
                    .tradeProduct(tradeProduct)
                    .category(category)
                    .build());
        }
        return wishCategoryEntities;
    }
}
