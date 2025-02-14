package com.app.treen.user.dto.response;

import com.app.treen.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {

    private Long id;

    private String loginId;

    private String name;
    private String phoneNum;
    private TokenResponseDto tokenResponse;

    @Builder
    public LoginResponseDto(User member, TokenResponseDto tokenResponse){
        this.id = member.getId();
        this.loginId = member.getLoginId();
        this.name = member.getUserName();
        this.phoneNum = member.getPhoneNum();
        this.tokenResponse = tokenResponse;
    }
}
