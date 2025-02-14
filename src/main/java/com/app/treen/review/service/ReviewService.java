package com.app.treen.review.service;

import com.app.treen.jpa.repository.products.TradeProductRepository;
import com.app.treen.jpa.repository.products.TransProductRepository;
import com.app.treen.jpa.repository.review.ReviewRepository;
import com.app.treen.jpa.repository.user.UserRepository;
import com.app.treen.products.entity.TradeProduct;
import com.app.treen.products.entity.TransProduct;
import com.app.treen.review.dto.ReviewRequestDto;
import com.app.treen.review.entity.Review;
import com.app.treen.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final TransProductRepository transProductRepository;
    private final TradeProductRepository tradeProductRepository;

    @Transactional
    public void createReview(ReviewRequestDto dto) {
        TransProduct transaction = null;
        TradeProduct trade = null;

        // 거래 유형에 따라 적절한 객체 설정
        if ("TRADE".equalsIgnoreCase(dto.getTransType())) {
            trade = tradeProductRepository.findById(dto.getTransactionId())
                    .orElseThrow(() -> new IllegalArgumentException("교환 거래를 찾을 수 없습니다."));
        } else if ("TRANSACTION".equalsIgnoreCase(dto.getTransType())) {
            transaction = transProductRepository.findById(dto.getTransactionId())
                    .orElseThrow(() -> new IllegalArgumentException("일반 거래를 찾을 수 없습니다."));
        } else {
            throw new IllegalArgumentException("유효하지 않은 거래 유형입니다: " + dto.getTransType());
        }

        // 리뷰 작성자 확인
        User reviewer = userRepository.findById(dto.getReviewerId())
                .orElseThrow(() -> new IllegalArgumentException("후기 작성자를 찾을 수 없습니다."));

        // 후기를 받는 사용자 확인
        User reviewedUser = userRepository.findById(dto.getReviewedUserId())
                .orElseThrow(() -> new IllegalArgumentException("후기를 받는 사용자를 찾을 수 없습니다."));

        // 기존 리뷰가 있는지 확인
        Optional<Review> existingReview = reviewRepository.findByTransactionId(dto.getTransactionId());
        if (existingReview.isPresent()) {
            throw new IllegalStateException("이미 작성된 거래 후기입니다.");
        }

        // 후기 저장
        Review review = Review.builder()
                .transaction(transaction) // 일반 거래 or null
                .trade(trade)             // 교환 거래 or null
                .reviewer(reviewer)
                .reviewedUser(reviewedUser)
                .rating(dto.getRating())
                .advantages(dto.getAdvantages())
                .content(dto.getContent())
                .transType(dto.getTransType()) // 거래 유형 저장
                .build();

        reviewRepository.save(review);
    }
}