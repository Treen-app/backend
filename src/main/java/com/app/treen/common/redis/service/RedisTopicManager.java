package com.app.treen.common.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisTopicManager {

    private final RedisMessageListenerContainer redisContainer;
    private final MessageListener messageListener;

    // 채널 추가 및 구독
    public void addTopic(Long roomId) {
        ChannelTopic channelTopic = new ChannelTopic("chatroom/" + roomId);
        redisContainer.addMessageListener(messageListener, channelTopic);
    }

    // 채널 제거 및 구독 취소
    public void removeTopic(Long roomId) {
        ChannelTopic channelTopic = new ChannelTopic("chatroom/" + roomId);
        redisContainer.removeMessageListener(messageListener, channelTopic);
    }

}
