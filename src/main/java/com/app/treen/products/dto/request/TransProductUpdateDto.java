package com.app.treen.products.dto.request;

import com.app.treen.products.entity.TransPImg;
import com.app.treen.products.entity.TransProduct;
import com.app.treen.products.entity.enumeration.Gender;
import com.app.treen.products.entity.enumeration.Method;
import com.app.treen.products.entity.enumeration.Size;
import com.app.treen.products.entity.enumeration.UsedRank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class TransProductUpdateDto {

    private String name;              // 상품 이름
    private String usedTerm;          // 사용 기간
    private Size size;                // 사이즈
    private String detail;            // 상세 설명
    private UsedRank usedRank;        // 사용 상태 등급
    private Method method;            // 거래 방법 (직거래/택배/모두 가능)
    private Long point;               // 상품 포인트
    private Gender gender;            // 대상 성별
    private Long categoryId;          // 카테고리 ID
    private List<String> imageUrls = new ArrayList<>(); // 이미지 URL 리스트

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<TransPImg> toImageEntities(TransProduct transProduct) {
        List<TransPImg> images = new ArrayList<>();
        for (int i = 0; i < imageUrls.size(); i++) {
            images.add(TransPImg.builder()
                    .transProduct(transProduct)
                    .imgUrl(imageUrls.get(i))
                    .sortOrder(i)
                    .isMain(i == 0) // 첫 번째 이미지를 대표 이미지로 설정
                    .build());
        }
        return images;
    }
}
