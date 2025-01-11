package com.app.treen.products.entity;

import com.app.treen.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransProductRepository extends JpaRepository<TransProduct,Long> {
    User findUserById(Long id);
}
