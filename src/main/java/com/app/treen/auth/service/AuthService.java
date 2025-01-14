package com.app.treen.auth.service;

import com.app.treen.auth.dto.RegisterDto;
import com.app.treen.auth.dto.request.LoginRequestDto;
import com.app.treen.auth.dto.response.LoginResponseDto;
import com.app.treen.auth.entity.Authority;
import com.app.treen.auth.entity.User;
import com.app.treen.auth.jwt.TokenProvider;
import com.app.treen.auth.repository.RedisRepository;
import com.app.treen.auth.repository.UserRepository;
import com.app.treen.common.response.code.BaseException;
import com.app.treen.common.response.code.status.ErrorStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecret;

    @Value("${coolsms.api.number}")
    private String fromPhoneNumber;

    private final RedisRepository redisRepository;
    private DefaultMessageService messageService;
    private final UserRepository userRepository;
    private final TokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @jakarta.annotation.PostConstruct
    public void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }

    public String sendSms(String userPhone) {
        String verificationCode = generateVerificationCode();

        Message message = new Message();
        message.setFrom(fromPhoneNumber);
        message.setTo(userPhone);
        message.setText("[Treen] 본인 확인 인증번호는 " + verificationCode + "입니다. 3분 내로 입력해주세요.");

        this.messageService.sendOne(new SingleMessageSendingRequest(message));
        redisRepository.createSmsVerification(userPhone, verificationCode);

        return verificationCode;
    }

    public void verifySms(String userPhone, String verifyCode) {
        if (!redisRepository.hasKey(userPhone)) {
            throw new IllegalArgumentException(ErrorStatus.AUTH_CODE_EXPIRED.getMessage());
        }

        String storedCode = redisRepository.getSmsVerification(userPhone);
        if (!storedCode.equals(verifyCode)) {
            throw new IllegalArgumentException(ErrorStatus.AUTH_CODE_NOT_MATCH.getMessage());
        }

        redisRepository.deleteSmsVerification(userPhone);
    }


    // 회원가입
    public RegisterDto signup(RegisterDto registerDto) {
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .userName(registerDto.getUsername())
                .loginId(registerDto.getLoginid())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .nickname(registerDto.getNickname())
                .birthDate(registerDto.getBirthDate())
                .build();

        return RegisterDto.from(userRepository.save(user));
    }

    private void validateUserDetails(String nickname, String id, String password, String confirmPassword) {
        if (!nickname.matches("^[가-힣a-zA-Z0-9]{1,10}$")) {
            throw new IllegalArgumentException(ErrorStatus.USER_NICKNAME_INVALID.getMessage());
        }

        if (!id.matches("^[a-z0-9]{5,12}$")) {
            throw new IllegalArgumentException(ErrorStatus.USER_ID_INVALID.getMessage());
        }

        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,15}$")) {
            throw new IllegalArgumentException(ErrorStatus.USER_PASSWORD_INVALID.getMessage());
        }

        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException(ErrorStatus.USER_PASSWORD_NOT_EQUAL.getMessage());
        }
    }

    private String generateVerificationCode() {
        Random rand = new Random();
        StringBuilder numStr = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            numStr.append(rand.nextInt(10));
        }
        return numStr.toString();
    }

    @Transactional(readOnly = true)
    public RegisterRequestDto getUserWithAuthorities(String username) {
        return RegisterRequestDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    private LoginResponseDto login(LoginRequestDto loginRequest) {
        User user = userRepository.findByLoginId(loginRequest.getId())
                .orElseThrow(() -> new BaseException(ErrorStatus.USER_NOT_FOUND));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BaseException(ErrorStatus.PASSWORD_NOT_MATCHED);
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getLoginId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getLoginId());

        return new LoginResponseDto(accessToken, refreshToken);
    }
}
