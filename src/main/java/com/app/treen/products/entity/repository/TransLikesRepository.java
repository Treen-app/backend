package com.app.treen.products.entity;

import com.app.treen.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransLikesRepository extends JpaRepository<TransLikes,Long> {

    boolean existsByUserAndTransProduct(User user, TransProduct product);

    Optional<Object> findByUserAndTransProduct(User user, TransProduct product);
}
