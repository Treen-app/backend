package com.app.treen.user.service;

import com.app.treen.common.response.code.status.ErrorStatus;
import com.app.treen.jpa.repository.user.UserRepository;
import com.app.treen.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        log.info("user loginId = {}", loginId);

        // 유저가 존재하지 않을 경우 예외 발생
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> {
                    log.error("사용자를 찾을 수 없습니다: {}", loginId);
                    return new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + loginId);
                });

        return new CustomUserDetails(user);
    }

}