package com.app.treen.products.entity;

import com.app.treen.products.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region,Long> {
    List<Region> findByTradeProduct(TradeProduct selectedProduct);
}
