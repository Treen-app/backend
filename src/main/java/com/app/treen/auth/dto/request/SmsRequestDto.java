package com.app.treen.auth.dto.request;

import lombok.Data;

@Data
public class SmsRequestDto {
    private String phoneNumber; // SMS 인증을 받을 전화번호
}