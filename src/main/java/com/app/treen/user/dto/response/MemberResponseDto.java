package com.app.treen.user.dto.response;

import com.app.treen.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class MemberResponseDto {
    public MemberResponseDto(User member) {
        this.id = member.getId();
        this.loginId = member.getLoginId();
        this.phoneNum = member.getPhoneNum();
        this.name = member.getUserName();
        this.profileImageUrl = member.getProfileImgUrl();
    }

    private final String loginId;

    private final Long id;

    private final String phoneNum;
    private final String name;

    private final String profileImageUrl;

    public static MemberResponseDto of(User member){
        return new MemberResponseDto(member);
    }

}
