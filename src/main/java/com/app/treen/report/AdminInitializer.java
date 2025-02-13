package com.app.treen.report;

import com.app.treen.jpa.repository.user.UserRepository;
import com.app.treen.user.entity.RoleType;
import com.app.treen.user.entity.User;
import com.app.treen.user.entity.UserStatus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (userRepository.findByLoginId("admin").isEmpty()) {
            User admin = User.builder()
                    .userName("Admin")
                    .loginId("admin")
                    .password(passwordEncoder.encode("greentreen21")) // 기본 비밀번호 설정
                    .phoneNum("01012345678")
                    .status(UserStatus.ACTIVE)
                    .roles(List.of(RoleType.ADMIN)) // 관리자 권한 부여
                    .build();
            userRepository.save(admin);
        }
    }
}