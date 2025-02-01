package com.app.treen.trade.trade_chat.message.service;

import com.app.treen.jpa.repository.user.UserRepository;
import com.app.treen.mongo.repository.TradeMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TradeMessageService {

    private final TradeMessageRepository tradeMessageRepository;
    private final UserRepository userRepository;
}
