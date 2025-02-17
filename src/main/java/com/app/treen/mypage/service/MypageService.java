package com.app.treen.mypage.service;

import com.app.treen.common.config.S3Uploader;
import com.app.treen.common.response.code.status.ErrorStatus;
import com.app.treen.common.response.exception.CustomException;
import com.app.treen.jpa.repository.review.ReviewRepository;
import com.app.treen.jpa.repository.user.UserProfileRepository;
import com.app.treen.jpa.repository.user.UserRepository;
import com.app.treen.mypage.dto.*;
import com.app.treen.review.entity.Review;
import com.app.treen.user.entity.User;
import com.app.treen.user.entity.UserProfile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ReviewRepository reviewRepository;
    private final S3Uploader s3Uploader;
    private final PasswordEncoder passwordEncoder;

    // 프로필 조회
    public MypageProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        UserProfile profile = userProfileRepository.findByUserId(userId).orElse(null);

        return new MypageProfileDto(user, profile); // User & UserProfile 동시 조회
    }

    // 프로필 수정
    @Transactional
    public void updateUserProfile(Long userId, UpdateUserProfileDto updateUserProfileDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseGet(() -> userProfileRepository.save(UserProfile.builder().user(user).build()));

        String imageUrl = null;
        if (updateUserProfileDto.getProfileImg() != null && !updateUserProfileDto.getProfileImg().isEmpty()) {
            try {
                imageUrl = s3Uploader.upload(updateUserProfileDto.getProfileImg(), "profile-images");
            } catch (Exception e) {
                throw new RuntimeException("이미지 업로드 실패: " + e.getMessage());
            }
        }

        profile.updateProfile(updateUserProfileDto, imageUrl);
        userProfileRepository.save(profile);
    }

    // 비밀번호 수정
    @Transactional
    public void changePassword(Long userId, ChangePasswordDto changePwDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(changePwDto.getCurrentPassword(), user.getPassword())) {
            throw new CustomException(ErrorStatus.PASSWORD_NOT_MATCHED);
        }

        // 새 비밀번호 암호화 후 저장
        user.changePassword(passwordEncoder.encode(changePwDto.getNewPassword()));
        userRepository.save(user);
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

