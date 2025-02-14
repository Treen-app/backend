package com.app.treen.products.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TradeRequestDto {

    private RegionRequestDto regionRequest;
    private ImgRequestDto imgRequest;
    private TradeProductSaveDto tradeProduct;
    private WishCategoryRequestDto wishCategoryRequest;

    public TradeRequestDto(TradeProductSaveDto tradeProduct, ImgRequestDto imgRequest, RegionRequestDto regionRequest, WishCategoryRequestDto wishCategoryRequest){
        this.regionRequest = regionRequest;
        this.tradeProduct = tradeProduct;
        this.imgRequest = imgRequest;
        this.wishCategoryRequest = wishCategoryRequest;
    }
}
