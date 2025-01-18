package com.app.treen.jpa.repository.user;

import com.app.treen.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
