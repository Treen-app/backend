package com.app.treen.products.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TransactionRequestDto {

    private TransProductSaveDto productRequest;  // ✅ 상품 요청 DTO 포함
    private RegionRequestDto regionRequest;      // ✅ 지역 요청 DTO 포함
    private TransImgRequestDto imageRequest;   // ✅ 이미지 요청 DTO 포함

    public TransactionRequestDto(TransProductSaveDto productRequest, RegionRequestDto regionRequest, TransImgRequestDto imageRequest) {
        this.productRequest = productRequest;
        this.regionRequest = regionRequest;
        this.imageRequest = imageRequest;
    }
}