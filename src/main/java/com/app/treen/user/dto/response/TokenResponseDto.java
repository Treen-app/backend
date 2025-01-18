package com.app.treen.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenResponseDto {
    String accessToken;
    String refreshToken;

    String key;
}
