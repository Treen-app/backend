package com.app.treen.jpa.repository.user;

import com.app.treen.user.entity.User;
import com.app.treen.user.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNum(String phoneNum);

    Optional<User> findByUserName(String name);

    Optional<User> findByLoginId(String loginId);

    Optional<User> findByLoginIdAndPhoneNum(String loginID, String phoneNum);

    Optional<User> findByUserNameAndPhoneNum(String userName, String phoneNum);

    List<User> findByStatus(UserStatus status);
}
