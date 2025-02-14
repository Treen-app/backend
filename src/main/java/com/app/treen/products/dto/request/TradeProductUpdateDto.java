package com.app.treen.products.dto.request;

import com.app.treen.products.entity.Category;
import com.app.treen.products.entity.TradePImg;
import com.app.treen.products.entity.TradeProduct;
import com.app.treen.products.entity.WishCategory;
import com.app.treen.products.entity.enumeration.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TradeProductUpdateDto {
    private String name;
    private String usedTerm;
    private Size size;
    private String detail;
    private UsedRank usedRank;
    private Method method;
    private Long point;
    private Gender gender;
    private String wishColor;
    private Size wishSize;
    private Long categoryId;

    private ImgRequestDto imgRequest;
    private WishCategoryRequestDto wishCategoryRequest;
    private RegionRequestDto regionRequest;

    private TradeType tradeType;

}
