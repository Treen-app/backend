package com.app.treen.trade.service;

import com.app.treen.common.response.code.status.ErrorStatus;
import com.app.treen.common.response.exception.CustomException;
import com.app.treen.jpa.repository.products.TradeProductRepository;
import com.app.treen.jpa.repository.trade.OfferedProductRepository;
import com.app.treen.jpa.repository.trade.TradeOfferRepository;
import com.app.treen.products.entity.TradeProduct;
import com.app.treen.trade.dto.request.TradeOfferSaveDto;
import com.app.treen.trade.dto.response.TradeOfferResponseDto;
import com.app.treen.trade.entity.OfferedProduct;
import com.app.treen.trade.entity.TradeOffer;
import com.app.treen.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TradeService {

    private final TradeOfferRepository tradeOfferRepository;
    private final OfferedProductRepository offeredProductRepository;
    private final TradeProductRepository tradeProductRepository;

    // 자유교환할 내 상품 목록 조회


    // 자유교환 요청 보내기
    @Transactional
    public Long saveTradeOffer(User loginUser, TradeOfferSaveDto tradeOfferSaveDto) {

        // 교환상품 리스트 생성
        List<OfferedProduct> offeredProducts = new ArrayList<>();

        // null 체크
        if (tradeOfferSaveDto.getOfferedProductId1() != null) {
            offeredProducts.add(offeredProductRepository.findById(tradeOfferSaveDto.getOfferedProductId1())
                    .orElseThrow(() -> new CustomException(ErrorStatus.PRODUCT_NOT_FOUND)));
        }
        if (tradeOfferSaveDto.getOfferedProductId2() != null) {
            offeredProducts.add(offeredProductRepository.findById(tradeOfferSaveDto.getOfferedProductId2())
                    .orElseThrow(() -> new CustomException(ErrorStatus.PRODUCT_NOT_FOUND)));
        }
        if (tradeOfferSaveDto.getOfferedProductId3() != null) {
            offeredProducts.add(offeredProductRepository.findById(tradeOfferSaveDto.getOfferedProductId3())
                    .orElseThrow(() -> new CustomException(ErrorStatus.PRODUCT_NOT_FOUND)));
        }

        // 판매 상품
        TradeProduct salesProduct = tradeProductRepository.findById(tradeOfferSaveDto.getSalesProductId())
                .orElseThrow(() -> new CustomException(ErrorStatus.PRODUCT_NOT_FOUND));

        // TradeOffer 생성 및 저장
        TradeOffer tradeOffer = TradeOffer.builder()
                .salesProduct(salesProduct)
                .offeredProductList(offeredProducts)
                .buyer(loginUser)
                .build();

        return tradeOfferRepository.save(tradeOffer).getId();
    }

    // 자유교환 보낸 요청 조회
    public List<TradeOfferResponseDto> findTradeRequest(User user) {

        List<TradeOffer> tradeOfferList = tradeOfferRepository.findAllByBuyer(user);
        List<TradeOfferResponseDto> tradeOfferResponseDtoList = new ArrayList<>();

        for (TradeOffer tradeOffer : tradeOfferList) {
            for (OfferedProduct offeredProduct : tradeOffer.getOfferedProductList()) {
                tradeOfferResponseDtoList.add(TradeOfferResponseDto.builder()
                        .tradeOfferId(tradeOffer.getId())
                        .createdDate(tradeOffer.getCreatedDate())
                        .salesProductId(tradeOffer.getSalesProduct().getId())
                        .salesProductName(tradeOffer.getSalesProduct().getName())
                        .offeredProduct(TradeOfferResponseDto.OfferedProductDto.from(offeredProduct.getOfferedProduct()))
                        .build());
            }
        }

        return tradeOfferResponseDtoList;
    }

    // 자유교환 요청 취소

    // 받은 자유교환 요청 목록 조회

    // 요청 승인 (자유교환 채팅 시작)

    // 자유교환 거래 예약

    // 자유교환 거래 예약 취소

    // 자유교환 거래 완료
}
