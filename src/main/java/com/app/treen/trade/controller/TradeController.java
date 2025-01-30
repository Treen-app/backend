package com.app.treen.trade.controller;

import com.app.treen.products.service.ProductService;
import com.app.treen.trade.dto.request.TradeOfferSaveDto;
import com.app.treen.trade.dto.response.TradeOfferResponseDto;
import com.app.treen.trade.service.TradeService;
import com.app.treen.transactions.dto.request.TransactionsSaveRequestDto;
import com.app.treen.transactions.dto.response.TransactionsResponseDto;
import com.app.treen.user.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trade")
public class TradeController {

    private final ProductService productService;
    private final TradeService tradeService;

    // 자유교환할 내 상품 목록 조회
//    @GetMapping("/my-trade-product")
//    public ResponseEntity<Void> getTradeProductList(@AuthenticationPrincipal CustomUserDetails userDetails,
//                                                    TransactionsSaveRequestDto transactionsSaveRequestDto) {
//
//    }

    // 자유교환 요청 보내기
    @PostMapping("auth/create")
    public ResponseEntity<Long> saveTradeOffer(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                    TradeOfferSaveDto tradeOfferSaveDto) {

        Long savedTradeOfferId = tradeService.saveTradeOffer(userDetails.getUser(), tradeOfferSaveDto);

        return ResponseEntity.status(HttpStatus.OK).body(savedTradeOfferId);
    }

    // 자유교환 보낸 요청 조회
    @GetMapping("auth/send/all")
    public ResponseEntity<List<TradeOfferResponseDto>> getAllTradeOfferSent(@AuthenticationPrincipal CustomUserDetails userDetails) {

        List<TradeOfferResponseDto> tradeOfferResponseDtoList = tradeService.findTradeRequest(userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(tradeOfferResponseDtoList);
    }

    // 자유교환 요청 취소

    // 받은 자유교환 요청 목록 조회

    // 요청 승인 (자유교환 채팅 시작)

    // 자유교환 거래 예약

    // 자유교환 거래 예약 취소

    // 자유교환 거래 완료

}
