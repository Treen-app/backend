package com.app.treen.products.entity.repository;

import com.app.treen.products.entity.TradeProduct;
import com.app.treen.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeProductRepository extends JpaRepository<TradeProduct,Long> {
    User findUserById(Long id);
}
