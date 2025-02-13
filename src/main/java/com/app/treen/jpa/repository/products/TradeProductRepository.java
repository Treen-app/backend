package com.app.treen.jpa.repository.products;

import com.app.treen.products.entity.TradeProduct;
import com.app.treen.user.entity.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeProductRepository extends JpaRepository<TradeProduct,Long> {
    User findUserById(Long id);

    void deleteByTradeProduct(TradeProduct existingProduct);
}
