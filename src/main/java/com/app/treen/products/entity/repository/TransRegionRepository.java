package com.app.treen.products.entity.repository;

import com.app.treen.products.entity.TransProduct;
import com.app.treen.products.entity.TransRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransRegionRepository extends JpaRepository<TransRegion,Long> {
    List<TransRegion> findByTransProduct(TransProduct selectedProduct);
}
