package com.app.treen.products.dto.request;
import com.app.treen.products.entity.Category;
import com.app.treen.products.entity.TradeProduct;
import com.app.treen.products.entity.WishCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class WishCategoryRequestDto {
    private List<Long> wishCategoryIds;

    public WishCategoryRequestDto(List<Long> wishCategoryIds) {
        this.wishCategoryIds = wishCategoryIds;
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
