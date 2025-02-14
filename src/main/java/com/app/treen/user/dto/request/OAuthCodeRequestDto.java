package com.app.treen.user.dto.request;

import lombok.Getter;

@Getter
public class OAuthCodeRequestDto {
    String code;
    String state;
}
