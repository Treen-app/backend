package com.app.treen.user.dto.request;

import com.app.treen.user.entity.RoleType;
import com.app.treen.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Collections;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class JoinRequestDto {
    @NotBlank(message = "아이디는 필수 입력 사항입니다.")
    @Pattern(regexp = "^[a-z0-9]{5,12}$", message = "아이디는 5~12자의 숫자 및 소문자만 가능합니다.")
    private final String loginId;

    @NotBlank(message = "닉네임은 필수 입력 사항입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{1,10}$", message = "닉네임은 1~10자의 한글, 숫자, 영어만 입력 가능합니다.")
    private final String name;

    @NotBlank(message = "전화번호는 필수 입력 사항입니다.")
    @Pattern(regexp = "^(01[0-9])-?([0-9]{3,4})-?([0-9]{4})$", message = "올바른 전화번호 형식을 입력하세요. (예: 010-1234-5678)")
    private final String phoneNumber;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~!@#$%^&*()+|=])[a-zA-Z0-9~!@#$%^&*()+|=]{8,15}$",
            message = "비밀번호는 8~15자의 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."
    )
    private final String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력 사항입니다.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~!@#$%^&*()+|=])[a-zA-Z0-9~!@#$%^&*()+|=]{8,15}$",
            message = "비밀번호를 다시 확인해주세요."
    )
    private final String password2;

    @Builder
    public JoinRequestDto(String loginId, String name, String phoneNumber, String password, String password2) {
        this.loginId = loginId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.password2 = password2;
    }

    public User toEntity(RoleType role, String encodedPassword) {
        return User.builder()
                .loginId(loginId)
                .userName(name)
                .phoneNum(phoneNumber)
                .password(encodedPassword)
                .loginType("default_login")
                .roles(Collections.singletonList(role))
                .build();
    }

}