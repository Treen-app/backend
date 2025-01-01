package com.app.treen.chatting.service;

import com.app.treen.chatting.dto.request.ChatRoomRequestDto;
import com.app.treen.chatting.dto.response.ChatRoomResponseDto;
import com.app.treen.chatting.entity.ChatRoom;
import com.app.treen.chatting.repository.ChatRoomRepository;
import com.app.treen.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    // 채팅방 생성
    @Transactional
    public ChatRoomResponseDto saveChatRoom(ChatRoomRequestDto requestDto) {
        ChatRoom savedChatRoom = requestDto.toEntity();
        return ChatRoomResponseDto.from(savedChatRoom);
    }

    // 채팅방 목록 조회
//    @Transactional
//    public List<ChatRoomResponseDto> findChatRoomList(User user) {
//        List<ChatRoom> chatRooms = chatRoomRepository.findByUser(user).orElse
//    }
}
