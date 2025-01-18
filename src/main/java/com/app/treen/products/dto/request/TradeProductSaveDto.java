package com.app.treen.products.dto.request;

import com.app.treen.products.entity.*;
import com.app.treen.products.entity.enumeration.*;
import com.app.treen.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TradeProductSaveDto {

    private String name;
    private String usedTerm;
    private Size size;
    private String detail;
    private UsedRank usedRank;
    private Method method;
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

    public TradeProduct toEntity(User user, Category category) {
        return TradeProduct.builder()
                .name(this.name)
                .user(user)
                .usedRank(this.usedRank)
                .detail(this.detail)
                .gender(this.gender)
                .method(this.method)
                .size(this.size)
                .usedTerm(this.usedTerm)
                .wishColor(this.wishColor)
                .wishSize(this.wishSize)
                .build();
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

    public List<TradeRegion> toRegionEntities(TradeProduct tradeProduct, List<Region> regions) {
        // tradeProduct와 regions가 null인 경우 예외를 던져서 처리할 수 있도록 한다.
        if (tradeProduct == null) {
            throw new IllegalArgumentException("TradeProduct cannot be null");
        }
        if (regions == null || regions.isEmpty()) {
            throw new IllegalArgumentException("Regions cannot be null or empty");
        }

        List<TradeRegion> tradeRegions = new ArrayList<>();
        for (Region region : regions) {
            // TradeRegion을 생성하고 tradeProduct와 region을 설정
            tradeRegions.add(TradeRegion.builder()
                    .tradeProduct(tradeProduct)  // tradeProduct 설정
                    .region(region)              // region 설정
                    .build());
        }
        return tradeRegions;
    }

}
