package com.app.treen.trade.service;

import com.app.treen.jpa.repository.trade.OfferedProductsRepository;
import com.app.treen.jpa.repository.trade.TradeRequestRepository;
import com.app.treen.trade.dto.request.TradeRequestSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TradeService {

    private final TradeRequestRepository tradeRepository;
    private final OfferedProductsRepository offeredProductsRepository;

    // 자유교환 신청 생성
    @Transactional
    public void saveTradeRequest(TradeRequestSaveDto dto) {

    }
}
