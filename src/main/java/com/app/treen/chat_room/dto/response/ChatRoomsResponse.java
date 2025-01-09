package com.app.treen.chat_room.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatRoomsResponse {

    List<ChatRoomResponseDto> chatRooms;
}
