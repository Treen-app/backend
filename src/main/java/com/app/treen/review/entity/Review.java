package com.app.treen.review.entity;

import com.app.treen.products.entity.TradeProduct;
import com.app.treen.products.entity.TransProduct;
import com.app.treen.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 일반 거래 (TRANSACTION)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private TransProduct transaction;

    // 교환 거래 (TRADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_id")
    private TradeProduct trade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private User reviewer; // 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_user_id", nullable = false)
    private User reviewedUser; // 후기를 받는 사용자

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewRating rating; // 거래 만족도

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<ReviewAdvantage> advantages; // 선택된 장점 (복수 선택 가능)

    @Column(length = 300)
    private String content; // 후기 내용

    @Column(nullable = false)
    private String transType; // 거래 유형 (TRANSACTION or TRADE)

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}