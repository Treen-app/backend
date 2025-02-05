package com.app.treen.mypage.dto;

import com.app.treen.user.entity.User;
import lombok.Getter;

@Getter
public class MypageProfileDto {
    private final String loginId;
    private final String name;
    private final Long point;
    private final String profileImageUrl;

    public MypageProfileDto(User user) {
        this.loginId = user.getLoginId();
        this.name = user.getUserName();
        this.point = user.getPoint();
        this.profileImageUrl = user.getProfileImgUrl();
    }
}
