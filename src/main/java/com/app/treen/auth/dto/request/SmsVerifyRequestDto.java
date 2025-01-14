package com.app.treen.auth.dto.request;

import lombok.Data;

@Data
public class SmsVerifyRequestDto {
    private String phoneNumber; // 인증할 전화번호
    private String code; // 입력된 인증 코드
}
