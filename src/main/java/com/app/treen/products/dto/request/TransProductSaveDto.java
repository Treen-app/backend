package com.app.treen.products.dto.request;

import com.app.treen.products.entity.*;
import com.app.treen.products.entity.enumeration.Gender;
import com.app.treen.products.entity.enumeration.Method;
import com.app.treen.products.entity.enumeration.Size;
import com.app.treen.products.entity.enumeration.UsedRank;
import com.app.treen.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class TransProductSaveDto {

    private String name;
    private String usedTerm;
    private Size size;
    private String detail;
    private UsedRank usedRank;
    private Method method;
    private Long point;
    private Gender gender;
    private Long categoryId;
    private List<String> imageUrls = new ArrayList<>();
    private List<Long> regionIds = new ArrayList<>();

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public TransProduct toEntity(User user, Category category) {
        return TransProduct.builder()
                .name(this.name)
                .category(category)
                .user(user)
                .usedRank(this.usedRank)
                .point(this.point)
                .detail(this.detail)
                .gender(this.gender)
                .method(this.method)
                .size(this.size)
                .usedTerm(this.usedTerm)
                .build();
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

    public List<TransRegion> toRegionEntities(TransProduct transProduct, List<Region> regions) {
        List<TransRegion> transRegions = new ArrayList<>();
        for (Region region : regions) {
            transRegions.add(TransRegion.builder()
                    .transProduct(transProduct)
                    .region(region)
                    .build());
        }
        return transRegions;
    }

}
