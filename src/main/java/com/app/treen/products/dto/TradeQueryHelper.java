package com.app.treen.products.dto;

import com.app.treen.products.entity.QTradeProduct;
import com.app.treen.products.entity.TradeProduct;
import com.app.treen.products.entity.enumeration.TradeType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;

public class TradeQueryHelper {

    /**
     * 거래 필터링 수행
     * @param condition
     * @param category
     * @param keyword
     * @param wishCategory
     * @param tradeType
     * @param product
     * @return BooleanBuilder
     */
    public static BooleanBuilder createFilterBuilder(
            Condition condition,
            Long category,
            String keyword,
            Long wishCategory,
            TradeType tradeType,
            QTradeProduct product
    ) {
        BooleanBuilder filterBuilder = new BooleanBuilder();

        // 조건 필터링
        addConditionFilters(condition, product, filterBuilder);

        // 카테고리 필터링
        addCategoryFilter(category, product, filterBuilder);

        // 검색어 필터링
        addKeywordFilter(keyword, product, filterBuilder);

        // WishCategory 필터링
        addWishCategoryFilter(wishCategory, product, filterBuilder);

        // TradeType 필터링
        addTradeTypeFilter(tradeType, product, filterBuilder);

        return filterBuilder;
    }

    private static void addConditionFilters(Condition condition, QTradeProduct product, BooleanBuilder filterBuilder) {
        if (condition != null) {
            if (condition == Condition.LATEST) {
                filterBuilder.and(product.createdDate.isNotNull());
            }
        }
    }

    private static void addCategoryFilter(Long category, QTradeProduct product, BooleanBuilder filterBuilder) {
        if (category != null) {
            filterBuilder.and(product.category.id.eq(category));
        }
    }

    private static void addKeywordFilter(String keyword, QTradeProduct product, BooleanBuilder filterBuilder) {
        if (keyword != null && !keyword.isEmpty()) {
            filterBuilder.and(product.name.containsIgnoreCase(keyword)
                    .or(product.detail.containsIgnoreCase(keyword)));
        }
    }

    private static void addWishCategoryFilter(Long wishCategory, QTradeProduct product, BooleanBuilder filterBuilder) {
        if (wishCategory != null) {
            // wishCategory.id와 일치하는 값 필터링
            filterBuilder.and(product.wishCategories.any().id.eq(wishCategory));
        }
    }

    private static void addTradeTypeFilter(TradeType tradeType, QTradeProduct product, BooleanBuilder filterBuilder) {
        if (tradeType != null) {
            filterBuilder.and(product.tradeType.eq(tradeType));
        }
    }

    /**
     * 정렬 조건 추가 (최신순만 적용)
     * @param product
     * @return OrderSpecifier
     */
    public static OrderSpecifier<?> getOrderSpecifier(QTradeProduct product) {
        return product.createdDate.desc(); // 최신순으로 정렬
    }

    public enum Condition {
        LATEST // 이제는 최신순만 지원
    }
}