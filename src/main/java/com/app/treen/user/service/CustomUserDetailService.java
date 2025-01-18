package com.app.treen.user.service;

import com.app.treen.common.response.code.status.ErrorStatus;
import com.app.treen.user.entity.User;
import com.app.treen.user.repository.UserRepository;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("user Name = {}", username);
        User user = userRepository.findByName(username)
                .orElseThrow(() -> {
                    log.error("사용자를 찾을 수 없습니다: {}", username);
                    return new UsernameNotFoundException(ErrorStatus.USER_NOT_FOUND.getMessage());
                });

        return new CustomUserDetails(user);
    }
}