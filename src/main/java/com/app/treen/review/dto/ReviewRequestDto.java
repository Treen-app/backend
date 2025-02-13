package com.app.treen.review.dto;

import com.app.treen.review.entity.ReviewAdvantage;
import com.app.treen.review.entity.ReviewRating;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReviewRequestDto {

    @NotNull
    private Long transactionId; // 거래 ID

    @NotNull
    private String transType; // 거래 유형 (TRANSACTION or TRADE)

    @NotNull
    private Long reviewerId; // 후기 작성자 ID

    @NotNull
    private Long reviewedUserId; // 후기를 받는 사용자 ID

    @NotNull
    private ReviewRating rating; // 거래 만족도

    private List<ReviewAdvantage> advantages; // 선택된 장점 (복수 선택 가능)

    @Max(300)
    private String content; // 구체적인 후기 (최대 300자)
}