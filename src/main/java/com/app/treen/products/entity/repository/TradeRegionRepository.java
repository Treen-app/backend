package com.app.treen.products.entity.repository;

import com.app.treen.products.entity.TradeProduct;
import com.app.treen.products.entity.TradeRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRegionRepository extends JpaRepository<TradeRegion,Long> {
    List<TradeRegion> findByTradeProduct(TradeProduct tradeProduct);
}
