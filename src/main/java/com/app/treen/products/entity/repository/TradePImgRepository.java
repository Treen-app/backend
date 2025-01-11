package com.app.treen.products.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TradePImgRepository extends JpaRepository<TradePImg,Long> {
    void deleteByTradeProduct(TradeProduct existingProduct);
}
