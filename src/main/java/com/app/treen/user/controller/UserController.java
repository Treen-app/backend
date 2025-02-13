package com.app.treen.user.controller;

import com.app.treen.user.dto.request.JoinRequestDto;
import com.app.treen.user.dto.request.LoginRequestDto;
import com.app.treen.user.dto.response.LoginResponseDto;
import com.app.treen.user.dto.response.MemberResponseDto;
import com.app.treen.user.service.CustomUserDetails;
import com.app.treen.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService memberService;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinRequestDto joinRequestDto){
        MemberResponseDto responseDto = memberService.signUp(joinRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto){
        LoginResponseDto memberResponse = memberService.login(loginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(memberResponse);
    }

    // 회원 정보 조회
    @GetMapping("/info")
    public ResponseEntity<?> getMember(@AuthenticationPrincipal CustomUserDetails userDetails){
        MemberResponseDto memberResponse = memberService.getMember(userDetails.getUser());
        return  ResponseEntity.status(HttpStatus.OK).body(memberResponse);
    }


    // 회원탈퇴

    // 핸드폰 인증

    // 카카오 로그인

    // 비밀번호 재설정


}