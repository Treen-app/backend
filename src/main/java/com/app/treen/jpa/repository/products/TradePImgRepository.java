package com.app.treen.jpa.repository.products;

import com.app.treen.products.entity.TradePImg;
import com.app.treen.products.entity.TradeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradePImgRepository extends JpaRepository<TradePImg,Long> {
    void deleteByTradeProduct(TradeProduct existingProduct);
}