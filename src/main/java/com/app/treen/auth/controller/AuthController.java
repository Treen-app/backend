package com.app.treen.auth.controller;

import com.app.treen.auth.dto.RegisterDto;
import com.app.treen.auth.dto.request.LoginRequestDto;
import com.app.treen.auth.dto.request.SmsRequestDto;
import com.app.treen.auth.dto.request.SmsVerifyRequestDto;
import com.app.treen.auth.dto.response.LoginResponseDto;
import com.app.treen.auth.service.AuthService;
import com.app.treen.common.response.ApiResponse;
import com.app.treen.common.response.code.status.SuccessStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    // 인증번호 전송
    @PostMapping("/send-sms")
    public ApiResponse<SmsRequestDto> sendSms(@RequestBody SmsRequestDto smsRequest) {
        authService.sendSms(smsRequest.getPhoneNumber());
        return ApiResponse.onSuccessWithoutResult(SuccessStatus.SMS_SENT_SUCCESS);
    }

    // 인증
    @PostMapping("/verify-sms")
    public ApiResponse<SmsVerifyRequestDto> verifySms(@RequestBody SmsVerifyRequestDto verifyRequest) {
        authService.verifySms(verifyRequest.getPhoneNumber(), verifyRequest.getCode());
        return ApiResponse.onSuccessWithoutResult(SuccessStatus.SMS_VERIFIED_SUCCESS);
    }

    // 회원가입
    @PostMapping("/signup")
    public ApiResponse<RegisterDto> signup(@RequestBody RegisterDto register) {
        return ApiResponse.onSuccessWithoutResult(SuccessStatus.REGISTRATION_SUCCESS);
    }

    // 로그인
    @PostMapping("/login")
    public ApiResponse<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        LoginResponseDto response = authService.login(loginRequest);
        return ApiResponse.onSuccess(response, SuccessStatus.LOGIN_SUCCESS);
    }

    @PostMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse<LoginResponseDto> getUserInfo(@PathVariable String username) {

    }
}
