package com.app.treen.auth.repository;

import com.app.treen.auth.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);

    boolean existsByToken(String token); // existsByToken 메서드 정의
    void deleteByToken(String token);
}