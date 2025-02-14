package com.app.treen.trade.service;

import com.app.treen.common.response.code.status.ErrorStatus;
import com.app.treen.common.response.exception.CustomException;
import com.app.treen.jpa.repository.products.TradeProductRepository;
import com.app.treen.jpa.repository.trade.OfferedProductRepository;
import com.app.treen.jpa.repository.trade.TradeOfferRepository;
import com.app.treen.products.entity.TradeProduct;
import com.app.treen.trade.dto.request.TradeOfferSaveDto;
import com.app.treen.trade.dto.response.ReceivedTradeOfferResponseDto;
import com.app.treen.trade.dto.response.SentTradeOfferResponseDto;
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
                .seller(salesProduct.getUser())
                .isAccomplished(false)
                .build();

        return tradeOfferRepository.save(tradeOffer).getId();
    }

    // 자유교환 보낸 요청목록 조회
    public List<SentTradeOfferResponseDto> findSentTradeOfferList(User user) {

        List<TradeOffer> tradeOfferList = tradeOfferRepository.findAllByBuyer(user);
        List<SentTradeOfferResponseDto> sentTradeOfferResponseDtoList = new ArrayList<>();

        for (TradeOffer tradeOffer : tradeOfferList) {
            // 성사되지 않은 거래만 목록에서 조회 가능
            if (!tradeOffer.isAccomplished()) {
                for (OfferedProduct offeredProduct : tradeOffer.getOfferedProductList()) {
                    sentTradeOfferResponseDtoList.add(SentTradeOfferResponseDto.builder()
                                    .tradeOfferId(tradeOffer.getId())
                                    .createdDate(tradeOffer.getCreatedDate())
                                    .myProduct(offeredProduct.getProduct().getName())
                                    .salesProduct(SentTradeOfferResponseDto.SalesProductDto.from(tradeOffer.getSalesProduct()))
                            .build());
                }
            }
        }

//        for (TradeOffer tradeOffer : tradeOfferList) {
//            // 성사되지 않은 거래만 목록에서 조회 가능
//            if (!tradeOffer.isAccomplished()) {
//                for (OfferedProduct offeredProduct : tradeOffer.getOfferedProductList()) {
//                    tradeOfferResponseDtoList.add(SentTradeOfferResponseDto.builder()
//                            .tradeOfferId(tradeOffer.getId())
//                            .createdDate(tradeOffer.getCreatedDate())
//                            .salesProductId(tradeOffer.getSalesProduct().getId())
//                            .salesProductName(tradeOffer.getSalesProduct().getName())
//                            .salesUserName(tradeOffer.getSalesProduct().getUser().getUserName())
//                            .offeredProduct(SentTradeOfferResponseDto.OfferedProductDto.from(offeredProduct.getOfferedProduct()))
//                            .build());
//                }
//            }
//        }

        return sentTradeOfferResponseDtoList;
    }

    // 자유교환 (보낸)요청 취소
    @Transactional
    public void deleteTradeOffer(User loginUser, Long tradeOfferId) {

        TradeOffer findTradeOffer = tradeOfferRepository.findById(tradeOfferId)
                .orElseThrow(() -> new CustomException(ErrorStatus.TRADE_NOT_FOUND));

        checkAuthTrade(loginUser, findTradeOffer);

        tradeOfferRepository.deleteById(tradeOfferId);
    }

    private void checkAuthTrade(User loginUser, TradeOffer tradeOffer) {

        // 교환신청자만 신청 취소 가능
        if (!tradeOffer.getBuyer().equals(loginUser)) {
            throw new CustomException(ErrorStatus.PERMISSION_DENIED_TRADE);
        }

        // 성사된 교환요청은 삭제할 수 없음
        if (!tradeOffer.isAccomplished()) {
            throw new CustomException(ErrorStatus.SHOULD_NOT_BE_ACCOMPLISHED);
        }

    }

    // 자유교환 받은 요청목록 조회
    public List<ReceivedTradeOfferResponseDto> findReceivedTradeOfferList(User loginUser) {

        List<TradeOffer> tradeOfferList = tradeOfferRepository.findAllBySeller(loginUser);
        List<ReceivedTradeOfferResponseDto> receivedTradeOfferResponseDtoList = new ArrayList<>();

        for (TradeOffer tradeOffer : tradeOfferList) {
            // 성사되지 않은 거래만 목록에서 조회 가능
            if (!tradeOffer.isAccomplished()) {
                for (OfferedProduct offeredProduct : tradeOffer.getOfferedProductList()) {
                    receivedTradeOfferResponseDtoList.add(ReceivedTradeOfferResponseDto.builder()
                                    .tradeOfferId(tradeOffer.getId())
                                    .createdDate(tradeOffer.getCreatedDate())
                                    .myProduct(tradeOffer.getSalesProduct().getName())
                                    .receivedProduct(ReceivedTradeOfferResponseDto.ReceivedProductDto.from(offeredProduct.getProduct()))
                            .build());
                }
            }
        }

        return receivedTradeOfferResponseDtoList;
    }

    // 요청 승인 (자유교환 채팅 시작)
//    @Transactional
//    public Long createChatting(User user, Long tradeOfferId) {
//
//
//    }

    // 자유교환 거래 예약

    // 자유교환 거래 예약 취소

    // 자유교환 거래 완료
}
