package com.app.treen.style.dto.request;

import com.app.treen.style.entity.Style;
import com.app.treen.style.entity.StyleImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StyleImageRequestDto {

    List<MultipartFile> images;
    public List<StyleImage> toImageEntities(Style style, List<String> imageUrls) {
        List<StyleImage> images = new ArrayList<>();
        for (int i = 0; i < imageUrls.size(); i++) {
            images.add(StyleImage.builder()
                    .style(style)
                    .imageUrl(imageUrls.get(i))
                    .sortOrder(i)
                    .isMain(i == 0) // 첫 번째 이미지를 대표 이미지로 설정
                    .build());
        }
        return images;
    }
}
