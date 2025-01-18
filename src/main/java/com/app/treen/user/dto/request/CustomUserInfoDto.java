package com.app.treen.user.dto.request;

import com.app.treen.user.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserInfoDto {
    Long memberId;
    String email;
    List<RoleType> roles;
}
