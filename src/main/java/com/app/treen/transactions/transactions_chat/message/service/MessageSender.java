package com.app.treen.transactions.transactions_chat.message.service;

import com.app.treen.kafka.dto.BrokerMessageDto;
import com.app.treen.transactions.transactions_chat.chat_room.dto.response.ChatRoomResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSender {

    private final KafkaTemplate<String, BrokerMessageDto> kafkaTemplate;

    public void send(String topic, BrokerMessageDto data) {
        kafkaTemplate.send(topic, data);
    }
}
