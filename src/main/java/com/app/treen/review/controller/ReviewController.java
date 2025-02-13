package com.app.treen.review.controller;

import com.app.treen.review.dto.ReviewRequestDto;
import com.app.treen.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 후기 작성 API (거래가 완료된 사용자만 가능)
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<String> createReview(@Valid @RequestBody ReviewRequestDto reviewRequestDto) {
        reviewService.createReview(reviewRequestDto);
        return ResponseEntity.ok("거래 후기가 등록되었습니다.");
    }
}