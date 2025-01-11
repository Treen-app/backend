package com.app.treen.products.entity.repository;

import com.app.treen.products.entity.TradeLikes;
import com.app.treen.products.entity.TradeProduct;
import com.app.treen.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TradeLikesRepository extends JpaRepository<TradeLikes,Long> {

    Optional<Object> findByUserAndTradeProduct(User user, TradeProduct tradeProduct);

    boolean existsByUserAndTradeProduct(User user, TradeProduct tradeProduct);
}
