package com.app.treen.common.config;

import com.app.treen.common.redis.service.RedisMessageStringSubscriber;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    /**
     * 메시지 발송을 위한 RedisTemplate
     */
    @Bean
    public RedisTemplate<String, String> stringValueRedisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    /**
     * 메시지 수신을 위한 설정: RedisMessageListenerContainer
     *
     * Redis의 channel 로부터 메시지를 수신받아 해당 MessageListenerAdapter 에게 디스패치
     */
    @Bean
    public RedisMessageListenerContainer redisContainer(
            RedisConnectionFactory redisConnectionFactory,
            MessageListenerAdapter messageStringListener) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        // PatternTopic을 사용하여 chatroom/* 형식의 모든 채널을 구독
        container.addMessageListener(messageStringListener, new PatternTopic("chatroom/*"));

        return container;
    }

    /**
     * 메시지 수신 리스너 어댑터 (메시지 핸들러를 설정)
     */
    @Bean
    public MessageListenerAdapter messageStringListener(SimpMessagingTemplate simpMessagingTemplate, ObjectMapper objectMapper) {
        return new MessageListenerAdapter(new RedisMessageStringSubscriber(simpMessagingTemplate, objectMapper), "onMessage");
    }


    @Primary
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());  // 이 부분을 확인
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

//    /**
//     * 채팅방 구독자들 간의 Topic 공유를 위해 Channel Topic 을 빈으로 등록해 단일화
//     */
//    @Bean
//    public ChannelTopic channelTopic(Long roomId) {
//        return new ChannelTopic("chatroom:" + roomId);
//    }

}
