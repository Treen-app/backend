package com.app.treen.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResetPasswordDto {
    private String loginId;
    private String phone;

}
