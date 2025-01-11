package com.app.treen.products.dto;

import com.app.treen.products.entity.QTransProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;

public class ProductQueryHelper {

    /**
     * 거래 필터링 수행
     * @param condition
     * @param category
     * @param keyword
     * @param product
     * @return
     */
    public static BooleanBuilder createFilterBuilder(
            Condition condition,
            Long category,
            String keyword,
            QTransProduct product
    ) {
        BooleanBuilder filterBuilder = new BooleanBuilder();

        // 조건 필터링
        addConditionFilters(condition, product, filterBuilder);

        // 카테고리 필터링
        addCategoryFilter(category, product, filterBuilder);

        // 검색어 필터링
        addKeywordFilter(keyword, product, filterBuilder);

        return filterBuilder;
    }

    private static void addConditionFilters(Condition condition, QTransProduct product, BooleanBuilder filterBuilder) {
        if (condition != null) {
            switch (condition) {
                case LATEST:
                    filterBuilder.and(product.createdDate.isNotNull());
                    break;
                case MORE_POINT:
                    filterBuilder.and(product.point.isNotNull().and(product.point.gt(0))); // 포인트가 0보다 큰 상품
                    break;
                case LESS_POINT:
                    filterBuilder.and(product.point.isNotNull().and(product.point.lt(0))); // 포인트가 0보다 작은 상품
                    break;
                default:
                    break;
            }
        }
    }

    private static void addCategoryFilter(Long category, QTransProduct product, BooleanBuilder filterBuilder) {
        if (category != null) {
            filterBuilder.and(product.category.id.eq(category));
        }
    }

    private static void addKeywordFilter(String keyword, QTransProduct product, BooleanBuilder filterBuilder) {
        if (keyword != null && !keyword.isEmpty()) {
            filterBuilder.and(product.name.containsIgnoreCase(keyword)
                    .or(product.detail.containsIgnoreCase(keyword)));
        }
    }

    /**
     * 정렬 조건 추가
     * @param condition
     * @param product
     * @return OrderSpecifier
     */
    public static OrderSpecifier<?> getOrderSpecifier(Condition condition, QTransProduct product) {
        if (condition != null) {
            switch (condition) {
                case LATEST:
                    return product.createdDate.desc();
                case MORE_POINT:
                    return product.point.desc();  // 포인트 높은 순
                case LESS_POINT:
                    return product.point.asc();   // 포인트 낮은 순
                default:
                    break;
            }
        }
        return product.createdDate.desc(); // 기본 정렬: 최신순
    }

    public enum Condition {
        LATEST, MORE_POINT, LESS_POINT
    }
}
