package com.app.treen.products.dto.request;


import com.app.treen.products.entity.TransPImg;
import com.app.treen.products.entity.TransProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class TransImgRequestDto {

    public List<TransPImg> toImageEntities(TransProduct transProduct, List<String> imageUrls) {
        List<TransPImg> images = new ArrayList<>();
        for (int i = 0; i <imageUrls.size();i++) {
            images.add(TransPImg.builder()
                            .transProduct(transProduct)
                            .imgUrl(imageUrls.get(i))
                            .sortOrder(i)
                            .isMain(i == 0)
                    .build());
        }
        return images;
    }

}
