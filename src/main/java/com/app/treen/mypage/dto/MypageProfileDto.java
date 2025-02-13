package com.app.treen.mypage.dto;

import com.app.treen.user.entity.User;
import com.app.treen.user.entity.UserProfile;
import lombok.Getter;

@Getter
public class MypageProfileDto {
    private final String loginId;
    private final String name;
    private final Long point;
    private final String profileImageUrl;

    // 추가된 UserProfile 필드
    private final String nickname;
    private final String gender;
    private final String birthDate;
    private final int height;
    private final int weight;
    private final int footSize;
    private final String clothingSize;

    public MypageProfileDto(User user, UserProfile profile) {
        this.loginId = user.getLoginId();
        this.name = user.getUserName();
        this.point = user.getPoint();
        this.profileImageUrl = user.getProfileImgUrl();

        // UserProfile 정보 추가
        this.nickname = profile != null ? profile.getNickname() : null;
        this.gender = profile != null ? profile.getGender() : null;
        this.birthDate = profile != null ? profile.getBirthDate().toString() : null;
        this.height = profile != null ? profile.getHeight() : 0;
        this.weight = profile != null ? profile.getWeight() : 0;
        this.footSize = profile != null ? profile.getFootSize() : 0;
        this.clothingSize = profile != null ? profile.getClothingSize() : null;
    }
}