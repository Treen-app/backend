package com.app.treen.jpa.repository.products;

import com.app.treen.products.entity.TransProduct;
import com.app.treen.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransProductRepository extends JpaRepository<TransProduct,Long> {
    User findUserById(Long id);
}
