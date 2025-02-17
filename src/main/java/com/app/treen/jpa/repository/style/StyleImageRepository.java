package com.app.treen.jpa.repository.style;

import com.app.treen.style.entity.Style;
import com.app.treen.style.entity.StyleImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StyleImageRepository extends JpaRepository<StyleImage,Long> {
    List<StyleImage> findByStyle(Style thisStyle);

    void deleteByStyle(Style thisStyle);

    StyleImage findByStyleAndIsMainTrue(Style style);
}
