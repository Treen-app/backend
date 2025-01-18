package com.app.treen.common.redis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 메시지 핸들러
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RedisMessageStringSubscriber {

    // Redis 메시지를 수신했을 때 호출
    public void onMessage(String message, String channel) {
        log.info("Received message: {} from channel: {}", message, channel);

        // 실시간 거래 데이터 처리

        // ===========

        // 알림데이터 파싱

        // 사용자 푸시 알림 전송

        // 알림 기록 저장
    }

}
