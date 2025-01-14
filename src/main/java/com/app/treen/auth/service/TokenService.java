package com.app.treen.auth.service;

import com.app.treen.auth.entity.RefreshToken;
import com.app.treen.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    // RefreshToken 저장소 주입
    private final RefreshTokenRepository refreshTokenRepository;

    // 리프레시 토큰 저장
    public void saveRefreshToken(String token, String username, long ttl) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .username(username)
                .ttl(ttl)
                .build();
        refreshTokenRepository.save(refreshToken);
    }

    // 리프레시 토큰 삭제
    public boolean deleteRefreshToken(String token) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
        if (refreshToken.isPresent()) {
            refreshTokenRepository.delete(refreshToken.get());
            return true;
        }
        return false;
    }

    // 리프레시 토큰 조회
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh Token not found"));
    }

    // 리프레시 토큰 존재 여부 확인
    public boolean existsByToken(String token) {
        return refreshTokenRepository.existsByToken(token);
    }
}
