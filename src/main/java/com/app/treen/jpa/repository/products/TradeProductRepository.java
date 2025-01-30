package com.app.treen.jpa.repository.products;

import com.app.treen.products.entity.TradeProduct;
import com.app.treen.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TradeProductRepository extends JpaRepository<TradeProduct,Long> {
    User findUserById(Long id);
}
