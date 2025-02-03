package com.app.treen.common.config;

import com.app.treen.common.redis.service.RedisMessageStringSubscriber;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisHost, redisPort);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {

        // redisTemplate 를 받아와서 set, get, delete 를 사용
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        /*
         * setKeySerializer, setValueSerializer 설정
         * redis-cli 을 통해 직접 데이터를 조회 시 알아볼 수 없는 형태로 출력되는 것을 방지
         */
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        return redisTemplate;
    }

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory();
//    }
//
//    /**
//     * 메시지 발송을 위한 RedisTemplate
//     */
//    @Bean
//    public RedisTemplate<String, String> stringValueRedisTemplate() {
//        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        return redisTemplate;
//    }
//
//    /**
//     * 메시지 수신을 위한 설정: RedisMessageListenerContainer
//     *
//     * Redis의 channel 로부터 메시지를 수신받아 해당 MessageListenerAdapter 에게 디스패치
//     */
//    @Bean
//    public RedisMessageListenerContainer redisContainer(
//            RedisConnectionFactory redisConnectionFactory,
//            MessageListenerAdapter messageStringListener) {
//
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(redisConnectionFactory);
//
//        // PatternTopic을 사용하여 chatroom/* 형식의 모든 채널을 구독
//        container.addMessageListener(messageStringListener, new PatternTopic("chatroom/*"));
//
//        return container;
//    }
//
//    /**
//     * 메시지 수신 리스너 어댑터 (메시지 핸들러를 설정)
//     */
//    @Bean
//    public MessageListenerAdapter messageStringListener(SimpMessagingTemplate simpMessagingTemplate, ObjectMapper objectMapper) {
//        return new MessageListenerAdapter(new RedisMessageStringSubscriber(simpMessagingTemplate, objectMapper), "onMessage");
//    }
//
//
//    @Primary
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());  // 이 부분을 확인
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        return redisTemplate;
//    }
//

}
