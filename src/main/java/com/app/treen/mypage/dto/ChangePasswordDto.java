package com.app.treen.mypage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangePasswordDto {

    @NotBlank(message = "전화번호는 필수 입력 사항입니다.")
    private String phoneNumber;

    @NotBlank(message = "현재 비밀번호는 필수 입력 사항입니다.")
    private String currentPassword;

    @NotBlank(message = "새 비밀번호는 필수 입력 사항입니다.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~!@#$%^&*()+|=])[a-zA-Z0-9~!@#$%^&*()+|=]{8,15}$",
            message = "비밀번호는 8~15자의 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."
    )
    private String newPassword;
}
