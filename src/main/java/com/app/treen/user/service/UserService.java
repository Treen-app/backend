package com.app.treen.user.service;

import com.app.treen.common.response.code.status.ErrorStatus;
import com.app.treen.common.response.exception.CustomException;
import com.app.treen.user.dto.request.CustomUserInfoDto;
import com.app.treen.user.dto.request.JoinRequestDto;
import com.app.treen.user.dto.request.LoginRequestDto;
import com.app.treen.user.dto.response.LoginResponseDto;
import com.app.treen.user.dto.response.MemberResponseDto;
import com.app.treen.user.dto.response.TokenResponseDto;
import com.app.treen.user.entity.RefreshToken;
import com.app.treen.user.entity.RoleType;
import com.app.treen.user.entity.User;
import com.app.treen.user.entity.repository.RefreshTokenRepository;
import com.app.treen.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveRefreshToken(TokenResponseDto tokenDto) {
        RefreshToken refreshToken = RefreshToken.builder().keyUserId(tokenDto.getKey()).refreshToken(tokenDto.getRefreshToken()).build();
        String userId = refreshToken.getKeyUserId();

        if (refreshTokenRepository.existsByKeyUserId(userId)) {
            refreshTokenRepository.deleteByKeyUserId(userId);
        }
        refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken getRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new CustomException(ErrorStatus.JWT_INVALID));
    }

    public String validateRefreshToken(String refreshToken) {
        RefreshToken getRefreshToken = getRefreshToken(refreshToken);
        String createdAccessToken = jwtProvider.validateRefreshToken(getRefreshToken);

        if (createdAccessToken == null) {
            throw new CustomException(ErrorStatus.JWT_EXPIRED);
        }

        return createdAccessToken;
    }

    // 회원가입
    public MemberResponseDto signUp(@Valid JoinRequestDto joinRequest) throws CustomException {
        String loginId = joinRequest.getLoginId();
        String name = joinRequest.getName();
        String phoneNum = joinRequest.getPhoneNumber();
        String password = passwordEncoder.encode(joinRequest.getPassword());

        // 닉네임 중복 확인
        if (userRepository.findByName(name).isPresent()) {
            throw new CustomException(ErrorStatus.USER_NICKNAME_DUPLICATED);
        }

        // 아이디 중복 확인
        if (userRepository.findByLoginId(loginId).isPresent()) {
            throw new CustomException(ErrorStatus.USER_ACCOUNT_DUPLICATED);
        }

        // 전화번호
        if (userRepository.findByPhoneNum(phoneNum).isPresent()) {
            throw new CustomException(ErrorStatus.USER_PHONE_IS_USED);
        }

        RoleType role = RoleType.USER;
        User member = joinRequest.toEntity(role, password);

        // 기본 프로필 이미지 설정
        final String DEFAULT_PROFILE_IMAGE_URL = "https://yourdomain.com/images/default-profile.png";
        member.setProfileImgUrl(DEFAULT_PROFILE_IMAGE_URL);

        userRepository.save(member); // 회원 정보 저장
        return new MemberResponseDto(member);
    }

    // 로그인
    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        String loginId = loginRequest.getLoginId();
        String password = loginRequest.getPassword();

        User member = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException(ErrorStatus.USER_ACCOUNT_NOT_MATCHED.getMessage()));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ErrorStatus.PASSWORD_NOT_MATCHED);
        }

        CustomUserInfoDto userInfoDto = new CustomUserInfoDto(member.getId(), member.getLoginId(), member.getRoles());
        TokenResponseDto tokenDto = jwtProvider.createTokenByLogin(userInfoDto);
        saveRefreshToken(tokenDto);
        return new LoginResponseDto(member,tokenDto);
    }


    // 멤버 정보 반환
    public MemberResponseDto getMember(User user) {
        MemberResponseDto memberResponse = new MemberResponseDto(user);
        return memberResponse;
    }
}
