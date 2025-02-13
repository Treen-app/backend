package com.app.treen.report.controller;

import com.app.treen.jpa.repository.user.UserRepository;
import com.app.treen.user.entity.User;
import com.app.treen.user.entity.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    // 정지된 계정 목록 조회
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/suspended-users")
    public ResponseEntity<List<User>> getSuspendedUsers() {
        List<User> suspendedUsers = userRepository.findByStatus(UserStatus.DEACTIVATED);
        return ResponseEntity.ok(suspendedUsers);
    }

    // 계정 정지 해제
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/unsuspend/{loginId}")
    public ResponseEntity<String> unsuspendUser(@PathVariable String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.changeStatusToActive();
        userRepository.save(user);

        return ResponseEntity.ok("사용자 정지가 해제되었습니다.");
    }
}