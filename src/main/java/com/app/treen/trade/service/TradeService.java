package com.app.treen.trade.service;

import com.app.treen.jpa.repository.trade.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TradeService {

    private final TradeRepository tradeRepository;

    // 자유교환 신청 생성
    @Transactional
    public void saveTradeRequest() {

    }
}
