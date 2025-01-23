package com.app.treen.jpa.repository.products;

import com.app.treen.products.entity.TradeProduct;
import com.app.treen.products.entity.WishCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishCategoryRepository extends JpaRepository<WishCategory,Long> {
    void deleteByTradeProduct(TradeProduct existingProduct);

    List<WishCategory> findAllByTradeProduct(TradeProduct selectedProduct);
}
