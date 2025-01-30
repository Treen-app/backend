package com.app.treen.jpa.repository.user;

import com.app.treen.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNum(String phoneNum);

    Optional<User> findByUserName(String name);

    Optional<User> findByLoginId(String loginId);

    Optional<User> findByLoginIdAndPhoneNum(String loginID, String phoneNum);

    Optional<User> findByUserNameAndPhoneNum(String userName, String phoneNum);
}
