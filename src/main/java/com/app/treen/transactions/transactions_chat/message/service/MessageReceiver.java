package com.app.treen.transactions.transactions_chat.message.service;

import com.app.treen.kafka.constants.KafkaConstants;
import com.app.treen.kafka.dto.BrokerMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageReceiver {

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = KafkaConstants.TRANS_CHAT_KAFKA_TOPIC, containerFactory = "kafkaListenerContainerFactory")
    public void receiveMessage(BrokerMessageDto message) {
        messagingTemplate.convertAndSend("/subscribe/" + message.getRoomId(), message);
    }
}
