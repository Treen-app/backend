package com.app.treen.auth.dto;

import com.app.treen.auth.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;
import software.amazon.awssdk.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotNull
    @Size(min = 2, max = 8)
    private String username; // 사용자 이름

    @NotNull
    @Size(min = 1, max = 10)
    private String nickname; // 사용자 닉네임

    @NotNull
    @Size(min = 5, max = 12)
    private String loginid; // 사용자 로그인 ID

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 8, max = 15)
    private String password; // 비밀번호

    @NotNull
    @Size(min = 8, max = 15)
    private String confirmPassword; // 비밀번호 확인

    private LocalDateTime birthDate; // 생년월일
    private String phoneNumber; // 사용자 전화번호

    private Set<AuthDto> authorityDtoSet;

    public static RegisterDto from(User user) {
        if(user == null) return null;

        return RegisterDto.builder()
                .phoneNumber(user.getPhone())
                .username(user.getUserName())
                .nickname(user.getNickname())
                .loginid(user.getLoginId())
                .password(user.getPassword())
                .birthDate(user.getBirthDate())
                .build();
    }
}
