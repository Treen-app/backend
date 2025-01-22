package com.app.treen.products.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionRequestDto {

    private TransProductSaveDto productRequest;
    private TransRegionRequestDto regionRequest;
    private TransImgRequestDto imageRequest;

    public TransactionRequestDto(TransProductSaveDto productRequest, TransRegionRequestDto regionRequest, TransImgRequestDto imageRequest) {
        this.productRequest = productRequest;
        this.regionRequest = regionRequest;
        this.imageRequest = imageRequest;
    }
}