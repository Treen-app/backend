package com.app.treen.user.entity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Repository
public class SmsCertificationDao {

    private String PREFIX = "sms";
    private final int LIMIT_TIME = 3 * 60;

    private final StringRedisTemplate redisTemplate;

    public void createSmsCertification(String phone, String certificationNumber){
        redisTemplate.opsForValue()
                .set(PREFIX + phone,certificationNumber, Duration.ofSeconds(LIMIT_TIME));
        log.info("인증번호 : " + certificationNumber);
    }

    public String getSmsCertification(String phone){
        return redisTemplate.opsForValue().get(PREFIX + phone);
    }

    public void removeSmsCertification(String phone) {
        redisTemplate.delete(PREFIX + phone);
    }

    public boolean hasKey(String phone) {
        return redisTemplate.hasKey(PREFIX + phone);
    }

}
