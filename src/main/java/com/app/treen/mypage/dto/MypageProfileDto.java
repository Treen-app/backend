package com.app.treen.mypage.dto;

import com.app.treen.products.entity.enumeration.Gender;
import com.app.treen.products.entity.enumeration.Size;
import com.app.treen.user.entity.User;
import com.app.treen.user.entity.UserProfile;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MypageProfileDto {
    private final String loginId;
    private final String name;
    private final Long point;
    private final String profileImageUrl;
    private final String nickname;
    private final Gender gender;
    private final LocalDate birthDate;
    private final int height;
    private final int weight;
    private final Size size;

    public MypageProfileDto(User user, UserProfile profile) {
        this.loginId = user.getLoginId();
        this.name = user.getUserName();
        this.point = user.getPoint();
        this.profileImageUrl = user.getProfileImgUrl();
        this.nickname = profile != null ? profile.getNickname() : null;
        this.gender = profile != null ? profile.getGender() : null;
        this.birthDate = profile != null ? profile.getBirthDate() : null;
        this.height = profile != null ? profile.getHeight() : 0;
        this.weight = profile != null ? profile.getWeight() : 0;
        this.size = profile != null ? profile.getSize() : null;
    }
}