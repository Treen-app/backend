package com.app.treen.jpa.repository.products;

import com.app.treen.products.entity.TransPImg;
import com.app.treen.products.entity.TransProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransPImgRepository extends JpaRepository<TransPImg,Long> {
    void deleteByTransProduct(TransProduct existingProduct);

    Optional<Object> findFirstByTransProductAndIsMainTrue(TransProduct product);

    List<TransPImg> findByTransProduct(TransProduct selectedProduct);
}
