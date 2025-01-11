package com.app.treen.products.entity.repository;

import com.app.treen.products.entity.TransPImg;
import com.app.treen.products.entity.TransProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransPImgRepository extends JpaRepository<TransPImg,Long> {
    void deleteByTransProduct(TransProduct existingProduct);
}
