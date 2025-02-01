package com.app.treen.trade.trade_chat.chat_room.service;

import com.app.treen.jpa.repository.trade.TradeChatRoomRepository;
import com.app.treen.mongo.repository.TradeMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TradeChatRoomService {

    private final TradeChatRoomRepository tradeChatRoomRepository;
    private final TradeMessageRepository tradeMessageRepository;



}
