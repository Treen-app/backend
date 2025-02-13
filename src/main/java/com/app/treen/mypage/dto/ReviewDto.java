package com.app.treen.mypage.dto;

import com.app.treen.review.entity.Review;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewDto {
    private Long id;
    private String reviewerName;
    private String rating;
    private String content;
    private String tradeType; // 거래 방식 (TRANSACTION or TRADE)
    private LocalDateTime createdAt;

    public static ReviewDto from(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .reviewerName(review.getReviewer().getUserName())
                .rating(review.getRating().name())
                .content(review.getContent())
                .tradeType(review.getTransType()) // 거래 방식 추가
                .createdAt(review.getCreatedAt())
                .build();
    }
}