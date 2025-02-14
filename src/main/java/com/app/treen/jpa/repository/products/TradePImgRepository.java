package com.app.treen.jpa.repository.products;

import com.app.treen.products.entity.TradePImg;
import com.app.treen.products.entity.TradeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface TradePImgRepository extends JpaRepository<TradePImg,Long> {
    void deleteByTradeProduct(TradeProduct existingProduct);

    Optional<TradePImg> findByTradeProductAndIsMainTrue(TradeProduct selectedProduct);

    List<TradePImg> findAllByTradeProduct(TradeProduct selectedProduct);

}
