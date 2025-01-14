package com.app.treen.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private String accessToken; // 액세스 토큰
    private String refreshToken; // 리프레시 토큰
}
