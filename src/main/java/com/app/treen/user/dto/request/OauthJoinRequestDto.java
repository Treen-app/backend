package com.app.treen.user.dto.request;

import com.app.treen.user.entity.RoleType;
import com.app.treen.user.entity.User;
import com.app.treen.user.entity.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;

@Getter
@NoArgsConstructor
public class OauthJoinRequestDto {
    private String accessToken;

}
