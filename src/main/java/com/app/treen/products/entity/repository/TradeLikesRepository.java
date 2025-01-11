package com.app.treen.products.entity;

import com.app.treen.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TradeLikesRepository extends JpaRepository<TradeLikes,Long> {
    boolean existsByUserAndTransProduct(User user, TradeProduct tradeProduct);

    Optional<Object> findByUserAndTradeProduct(User user, TradeProduct tradeProduct);
}
