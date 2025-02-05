package com.app.treen.mypage.service;

import com.app.treen.jpa.repository.user.UserProfileRepository;
import com.app.treen.jpa.repository.user.UserRepository;
import com.app.treen.mypage.dto.MypageProfileDto;
import com.app.treen.mypage.dto.TransactionHistoryDto;
import com.app.treen.mypage.dto.TradeHistoryDto;
import com.app.treen.mypage.dto.UpdateUserProfileDto;
import com.app.treen.user.entity.User;
import com.app.treen.user.entity.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public MypageProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return new MypageProfileDto(user);
    }

    @Transactional
    public void updateUserProfile(Long userId, UpdateUserProfileDto updateUserProfileDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 프로필이 없으면 새로 생성
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserProfile newProfile = UserProfile.builder()
                            .user(user)
                            .nickname(updateUserProfileDto.getNickname())
                            .gender(updateUserProfileDto.getGender())
                            .birthDate(LocalDate.parse(updateUserProfileDto.getBirthDate()))
                            .height(updateUserProfileDto.getHeight())
                            .weight(updateUserProfileDto.getWeight())
                            .footSize(updateUserProfileDto.getFootSize())
                            .clothingSize(updateUserProfileDto.getClothingSize())
                            .build();
                    return userProfileRepository.save(newProfile);
                });

        // 기존 프로필 업데이트
        profile.updateProfile(updateUserProfileDto);
        userProfileRepository.save(profile);
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

