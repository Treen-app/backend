package com.app.treen.products.dto.request;

import com.app.treen.products.entity.*;
import com.app.treen.products.entity.enumeration.*;
import com.app.treen.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class TradeProductSaveDto {

    private String name;
    private String usedTerm;
    private Size size;
    private String detail;
    private UsedRank usedRank;
    private Method method;
    private Gender gender;
    private String wishColor;
    private Size wishSize;
    private TradeType tradeType;
    private Long categoryId;

    private List<String> imageUrls = new ArrayList<>(); // 이미지 리스트
    private List<Long> wishCategoryIds = new ArrayList<>(); // 희망 카테고리 리스트
    private List<Long> regionIds = new ArrayList<>(); // 거래 가능 지역 리스트

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = (imageUrls != null) ? imageUrls : new ArrayList<>();
    }

    public void setWishCategoryIds(List<Long> wishCategoryIds) {
        this.wishCategoryIds = (wishCategoryIds != null) ? wishCategoryIds : new ArrayList<>();
    }

    public void setRegionIds(List<Long> regionIds) {
        this.regionIds = (regionIds != null) ? regionIds : new ArrayList<>();
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
                .tradeType(this.tradeType)
                .category(category)
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
        if (tradeProduct == null) {
            throw new IllegalArgumentException("TradeProduct cannot be null");
        }
        if (regions == null || regions.isEmpty()) {
            throw new IllegalArgumentException("Regions cannot be null or empty");
        }

        List<TradeRegion> tradeRegions = new ArrayList<>();
        for (Region region : regions) {
            tradeRegions.add(TradeRegion.builder()
                    .tradeProduct(tradeProduct)
                    .region(region)
                    .build());
        }
        return tradeRegions;
    }

}
