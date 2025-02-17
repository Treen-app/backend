package com.app.treen.mypage.controller;

import com.app.treen.common.response.code.status.SuccessStatus;
import com.app.treen.mypage.dto.*;
import com.app.treen.mypage.service.MypageService;
import com.app.treen.user.service.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    @Operation(summary = "마이페이지 프로필 조회")
    @GetMapping("/profile")
    public ResponseEntity<MypageProfileDto> getUserProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(mypageService.getUserProfile(userDetails.getUser().getId()));
    }

    @Operation(summary = "사용자 정보 수정")
    @PutMapping(value = "/profile/update")
    public ResponseEntity<?> updateUserProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute UpdateUserProfileDto updateUserProfileDto) {
        mypageService.updateUserProfile(userDetails.getUser().getId(), updateUserProfileDto);
        return ResponseEntity.ok(SuccessStatus.PROFILE_UPDATE_SUCCESS);
    }

    @Operation(summary = "비밀번호 변경")
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ChangePasswordDto changePwDto) {
        mypageService.changePassword(userDetails.getUser().getId(), changePwDto);
        return ResponseEntity.ok(SuccessStatus.PASSWORD_CHANGE_SUCCESS);
    }

    @Operation(summary = "내가 받은 후기 조회")
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewDto>> getReceivedReviews(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(mypageService.getReceivedReviews(userDetails.getUser().getId()));
    }

    @Operation(summary = "거래 내역 조회")
    @GetMapping("/transaction/history")
    public ResponseEntity<List<TransactionHistoryDto>> getTransactionHistory(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(mypageService.getTransactionHistory(userDetails.getUser().getId()));
    }

    @Operation(summary = "교환 내역 조회")
    @GetMapping("/trade/history")
    public ResponseEntity<List<TradeHistoryDto>> getTradeHistory(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(mypageService.getTradeHistory(userDetails.getUser().getId()));
    }
}
