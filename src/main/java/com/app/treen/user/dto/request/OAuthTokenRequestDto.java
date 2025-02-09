package com.app.treen.user.dto.request;

import lombok.Getter;

@Getter
public class OAuthTokenRequestDto {
    private String token_type;
    private String access_token;
    private Integer expires_in;
    private String refresh_token;
    private String refresh_token_expires_in;
    private String scope;
}
