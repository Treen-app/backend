package com.app.treen.common.redis.chat_room_connection.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChatRoomConnectionRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final String CHAT_ROOM_KEY = "CHAT_ROOM_CONNECTIONS"; // Redis 키 네임

    // 채팅방에 유저 접속
    public void addUserToRoom(Long roomId, Long userId) {
        redisTemplate.opsForSet().add(CHAT_ROOM_KEY + ":" + roomId, userId.toString());
    }

    // 채팅방에서 유저 접속 해제
    public void removeUserFromRoom(Long roomId, Long userId) {
        redisTemplate.opsForSet().remove(CHAT_ROOM_KEY + ":" + roomId, userId.toString());
    }

    // 특정 채팅방의 접속자 수 반환. -> 2일 경우 상대방도 접속 O, 1일 경우 상대방 접속 X
    public long getUsersCountInRoom(Long roomId) {
        return redisTemplate.opsForSet().size(CHAT_ROOM_KEY + ":" + roomId);
    }

    // 특정 사용자가 특정 채팅방에 접속해 있는지 확인
    public boolean isUserInRoom(Long roomId, Long userId) {
        return redisTemplate.opsForSet().isMember(CHAT_ROOM_KEY + ":" + roomId, userId.toString());
    }

}
