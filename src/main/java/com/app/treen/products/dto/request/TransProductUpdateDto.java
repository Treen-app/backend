package com.app.treen.products.dto.request;

import com.app.treen.products.entity.TransRegion;
import com.app.treen.products.entity.enumeration.Gender;
import com.app.treen.products.entity.enumeration.Method;
import com.app.treen.products.entity.enumeration.Size;
import com.app.treen.products.entity.enumeration.UsedRank;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class TransProductUpdateDto {
    public TransProductUpdateDto(String name, String usedTerm, Size size, String detail, UsedRank usedRank, Method method, Long point, Gender gender, Long categoryId, TransRegionRequestDto regionRequest, TransImgRequestDto imgRequest) {
        this.name = name;
        this.usedTerm = usedTerm;
        this.size = size;
        this.detail = detail;
        this.usedRank = usedRank;
        this.method = method;
        this.point = point;
        this.gender = gender;
        this.categoryId = categoryId;
        this.regionRequest = regionRequest;
        this.imgRequest = imgRequest;
    }

    private String name;              // 상품 이름
    private String usedTerm;          // 사용 기간
    private Size size;                // 사이즈
    private String detail;            // 상세 설명
    private UsedRank usedRank;        // 사용 상태 등급
    private Method method;            // 거래 방법 (직거래/택배/모두 가능)
    private Long point;               // 상품 포인트
    private Gender gender;            // 대상 성별
    private Long categoryId;          // 카테고리 ID

    private TransRegionRequestDto regionRequest;
    private TransImgRequestDto imgRequest;

}
