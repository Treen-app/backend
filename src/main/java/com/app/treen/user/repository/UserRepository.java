package com.app.treen.user.repository;

import com.app.treen.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);

    Optional<User> findByLoginId(String email);

    Optional<Object> findByPhoneNum(String phoneNum);
}
