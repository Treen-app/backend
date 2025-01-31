package com.app.treen.user.controller;

import com.app.treen.common.response.code.BaseCode;
import com.app.treen.common.response.code.status.ErrorStatus;
import com.app.treen.common.response.code.status.SuccessStatus;
import com.app.treen.common.response.exception.CustomException;
import com.app.treen.user.dto.request.*;
import com.app.treen.user.dto.response.FindLoginIdResponseDto;
import com.app.treen.user.dto.response.LoginResponseDto;
import com.app.treen.user.dto.response.MemberResponseDto;
import com.app.treen.user.service.CustomUserDetails;
import com.app.treen.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원 api")
@RequestMapping("/api/auth")
public class UserController {

    private final UserService memberService;

    // 회원가입
    @Operation(summary = "회원가입 API")
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinRequestDto joinRequestDto){
        MemberResponseDto responseDto = memberService.signUp(joinRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 로그인
    @Operation(summary = "로그인 API")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto){
        LoginResponseDto memberResponse = memberService.login(loginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(memberResponse);
    }

    // 회원 정보 조회
    @Operation(summary = "회원정보 조회 API")
    @GetMapping("/info")
    public ResponseEntity<?> getMember(@AuthenticationPrincipal CustomUserDetails userDetails){
        MemberResponseDto memberResponse = memberService.getMember(userDetails.getUser());
        return  ResponseEntity.status(HttpStatus.OK).body(memberResponse);
    }

    // 회원탈퇴
    @Operation(summary = "회원 탈퇴 API")
    @PutMapping("/sign-out")
    public ResponseEntity<?> signOut(@AuthenticationPrincipal CustomUserDetails userDetails){
        memberService.deleteUser(userDetails.getUser());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // 핸드폰 인증
    @Operation(summary = "전화번호 인증번호 전송 API")
    @PostMapping("/sms/send")
    public ResponseEntity<?> sendSms(@RequestBody smsCertificationDto.CertificationNumRequest requestDto){
        try{
            memberService.sendSms(requestDto);
            return ResponseEntity.status(HttpStatus.OK).body(SuccessStatus.SMS_CERTIFICATION_SUCCESS);
        } catch (Exception e){
            throw new CustomException(ErrorStatus._INTERNAL_SERVER_ERROR);
        }
    }

    // 인증번호 확인
    @Operation(summary = "인증번호 확인 API")
    @PostMapping("/sms/confirm")
    public ResponseEntity<?> smsVertification(@RequestBody smsCertificationDto.SmsCertificationRequest request){
        try {
            memberService.verifySms(request);
            return ResponseEntity.status(HttpStatus.OK).body(SuccessStatus.SMS_CERTIFICATION_SUCCESS);
        } catch (Exception e){
            throw new CustomException(ErrorStatus._INTERNAL_SERVER_ERROR);
        }
    }

    // 카카오 로그인

    // 비밀번호 재설정
    @Operation(summary = "비밀번호 재설정 API")
    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto requestDto) throws Exception{
        memberService.resetPassword(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessStatus.PASSWORD_RESET_SUCCESS);
    }

    // 아이디 찾기
    @Operation(summary = "아이디 찾기 API")
    @PostMapping("/loginId/find")
    public ResponseEntity<?> findLoginId(@RequestBody FindIdRequestDto requestDto) {
        FindLoginIdResponseDto responseDto = memberService.findLoginId(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}