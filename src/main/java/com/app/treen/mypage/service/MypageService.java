package com.app.treen.mypage.service;

import com.app.treen.jpa.repository.review.ReviewRepository;
import com.app.treen.jpa.repository.user.UserProfileRepository;
import com.app.treen.jpa.repository.user.UserRepository;
import com.app.treen.mypage.dto.*;
import com.app.treen.review.entity.Review;
import com.app.treen.user.entity.User;
import com.app.treen.user.entity.UserProfile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ReviewRepository reviewRepository;

    public MypageProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        UserProfile profile = userProfileRepository.findByUserId(userId).orElse(null);

        return new MypageProfileDto(user, profile); // User & UserProfile 동시 조회
    }

    @Transactional
    public void updateUserProfile(Long userId, UpdateUserProfileDto updateUserProfileDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 기존 프로필 조회, 없으면 새로 생성
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserProfile newProfile = UserProfile.builder()
                            .user(user)
                            .nickname(updateUserProfileDto.getNickname())
                            .gender(updateUserProfileDto.getGender()) // 🚀 String 그대로 사용 가능
                            .birthDate(LocalDate.parse(updateUserProfileDto.getBirthDate())) // 🚀 직접 변환
                            .height(updateUserProfileDto.getHeight())
                            .weight(updateUserProfileDto.getWeight())
                            .footSize(updateUserProfileDto.getFootSize())
                            .clothingSize(updateUserProfileDto.getClothingSize())
                            .build();
                    return userProfileRepository.save(newProfile);
                });

        // 기존 프로필 업데이트
        profile.updateProfile(updateUserProfileDto); // DTO 전달하여 업데이트 수행

        userProfileRepository.save(profile);
    }

    // 내가 받은 후기 조회
    @Transactional
    public List<ReviewDto> getReceivedReviews(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 내가 받은 후기 조회
        List<Review> reviews = reviewRepository.findByReviewedUser(user);

        return reviews.stream()
                .map(ReviewDto::from)
                .collect(Collectors.toList());
    }

    public List<TransactionHistoryDto> getTransactionHistory(Long userId) {
        // 거래 내역 조회
        return List.of();
    }

    public List<TradeHistoryDto> getTradeHistory(Long userId) {
        // 교환 내역 조회
        return List.of();
    }
}

