package com.app.treen.mypage.dto;

import com.app.treen.products.entity.enumeration.Gender;
import com.app.treen.products.entity.enumeration.Size;
import com.app.treen.user.entity.User;
import com.app.treen.user.entity.UserProfile;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MypageProfileDto {
    private final String userName;
    private final String loginId;
    private final String nickname;
    private final String phone;
    private final Gender gender;
    private final LocalDate birthDate;
    private final int height;
    private final int weight;
    private final Size size;
    private final Long treen;
    private final String imgUrl;
    private final LocalDate createdDate;

    public MypageProfileDto(User user, UserProfile profile) {
        this.userName = user.getUserName();
        this.loginId = user.getLoginId();
        this.phone = user.getPhoneNum();
        this.treen = user.getPoint();
        this.createdDate = user.getCreatedDate();

        this.nickname = (profile != null) ? profile.getNickname() : null;
        this.gender = (profile != null) ? profile.getGender() : null;
        this.birthDate = (profile != null) ? profile.getBirthDate() : null;
        this.height = (profile != null) ? profile.getHeight() : 0;
        this.weight = (profile != null) ? profile.getWeight() : 0;
        this.size = (profile != null) ? profile.getSize() : null;
        this.imgUrl = (profile != null) ? profile.getImgUrl() : null;
    }
}