package com.app.treen.auth.repository;

import com.app.treen.auth.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
    boolean existsByNickname(String nickname);
    boolean existsByLoginId(String loginId);

    @EntityGraph(attributePaths = "authorities")	// Eager(즉시)조회로 authorites 정보를 같이 가져온다.
        // username을 기준으로 User 정보를 가져올때 권한 정보도 같이 가져온다.
    Optional<User> findOneWithAuthoritiesByUsername(String username);
}