package com.app.treen.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class RedisRepository {
    private final String PREFIX = "sms:"; // Redis 키에 사용할 접두사
    private final int LIMIT_TIME = 3 * 60; // 인증번호 유효 시간(초)

    private final StringRedisTemplate stringRedisTemplate; // Redis 작업을 위한 StringRedisTemplate 객체

    // Redis에 저장
    public void createSmsVerification(String userPhone, String verificationCode) {
        stringRedisTemplate.opsForValue().set(PREFIX + userPhone, verificationCode, Duration.ofSeconds(LIMIT_TIME));
    }

    // 휴대전화 번호에 해당하는 인증번호 불러오기
    public String getSmsVerification(String userPhone) {
        return stringRedisTemplate.opsForValue().get(PREFIX + userPhone);
    }

    // 인증 완료 시, 인증번호 Redis에서 삭제
    public void deleteSmsVerification(String userPhone) {
        stringRedisTemplate.delete(PREFIX + userPhone);
    }

    // Redis에 해당 휴대번호로 저장된 인증번호가 존재하는지 확인
    public boolean hasKey(String userPhone) {
        return stringRedisTemplate.hasKey(PREFIX + userPhone);
    }
}
