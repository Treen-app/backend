package com.app.treen.user.service;

import com.app.treen.common.response.code.status.ErrorStatus;
import com.app.treen.common.response.exception.CustomException;
import com.app.treen.jpa.repository.user.UserRepository;
import com.app.treen.user.dto.request.*;
import com.app.treen.user.dto.response.FindLoginIdResponseDto;
import com.app.treen.user.dto.response.LoginResponseDto;
import com.app.treen.user.dto.response.MemberResponseDto;
import com.app.treen.user.dto.response.TokenResponseDto;
import com.app.treen.user.entity.RoleType;
import com.app.treen.user.entity.SmsCertificationDao;
import com.app.treen.user.entity.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private final SmsCertificationUtil smsUtil;
    private final SmsCertificationDao smsCertificationDao;


    // 회원가입
    public MemberResponseDto signUp(@Valid JoinRequestDto joinRequest) throws CustomException {
        String loginId = joinRequest.getLoginId();
        String name = joinRequest.getName();
        String phoneNum = joinRequest.getPhoneNumber();
        String password = passwordEncoder.encode(joinRequest.getPassword());

        // 닉네임 중복 확인
        if (userRepository.findByUserName(name).isPresent()) {
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

        if (password.equals(joinRequest.getPassword2())) {
            throw new CustomException(ErrorStatus.PASSWORD_NOT_MATCHED);
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

        User member = (User) userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_ACCOUNT_NOT_MATCHED));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ErrorStatus.PASSWORD_NOT_MATCHED);
        }

        CustomUserInfoDto userInfoDto = new CustomUserInfoDto(member.getId(), member.getLoginId(), member.getRoles());
        TokenResponseDto tokenDto = jwtProvider.createTokenByLogin(userInfoDto);
        //saveRefreshToken(tokenDto);
        return new LoginResponseDto(member,tokenDto);
    }


    // 멤버 정보 반환
    public MemberResponseDto getMember(User user) {
        MemberResponseDto memberResponse = new MemberResponseDto(user);
        return memberResponse;
    }

    // 비밀번호 재설정
    @Transactional
    public void resetPassword(ResetPasswordDto requestDto) throws Exception {
        User user = userRepository.findByLoginIdAndPhoneNum(requestDto.getLoginId(), requestDto.getPhone())
                .orElseThrow(()->new CustomException(ErrorStatus.USER_NOT_FOUND));
        if (user == null) {
            throw new CustomException(ErrorStatus.USER_NOT_FOUND);
        }
        String tempPassword = generateTempPassword(12);
        String encodedPassword = passwordEncoder.encode(tempPassword);
        user.changePassword(encodedPassword);
        userRepository.save(user);
        smsUtil.sendRestPassword(requestDto.getPhone(), tempPassword);
    }

    // 임시 비밀번호 생성
    private String generateTempPassword(int length) throws Exception {

        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!@#$%^&*()-_=+[]{}|;:',.<>?/";
        String all = upper + lower + digits + special;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        // 각 조건을 만족하는 문자를 반드시 포함
        password.append(upper.charAt(random.nextInt(upper.length())));
        password.append(lower.charAt(random.nextInt(lower.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(special.charAt(random.nextInt(special.length())));

        for (int i = 4; i < length; i++) {
            password.append(all.charAt(random.nextInt(all.length())));
        }

        // 문자열을 섞어서 랜덤화
        char[] pwdArray = password.toString().toCharArray();
        for (int i = pwdArray.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = pwdArray[index];
            pwdArray[index] = pwdArray[i];
            pwdArray[i] = temp;
        }

        return new String(pwdArray);
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(User user) {
        if (user == null) {
            throw new CustomException(ErrorStatus.USER_NOT_FOUND);
        }
        user.changeStatusToDeleted();
        userRepository.save(user);
    }

    // 전화번호 인증
    public void sendSms(smsCertificationDto.CertificationNumRequest requestDto) {
        String to = requestDto.getPhoneNum();
        int randomNumber = (int)(Math.random() * 9000) + 1000;
        String certificationNumber = String.valueOf(randomNumber);
        smsUtil.sendSms(to, certificationNumber);
        smsCertificationDao.createSmsCertification(to,certificationNumber);
    }

    // 인증번호 확인
    public void verifySms(smsCertificationDto.SmsCertificationRequest requestDto) {
        if (isVerify(requestDto)) {
            throw new CustomException(ErrorStatus.CERTIFICATION_NUMBER_NOT_MATCHED);
        }
    }

    // 인증번호 일치 확인
    public boolean isVerify(smsCertificationDto.SmsCertificationRequest request) {
        return !(smsCertificationDao.hasKey(request.getPhone()) &&
                smsCertificationDao.getSmsCertification(request.getPhone())
                        .equals(request.getCertificationNumber()));
    }

    // 아이디 찾기
    public FindLoginIdResponseDto findLoginId(FindIdRequestDto requestDto) {
        User thisUser = userRepository.findByUserNameAndPhoneNum(requestDto.getUserName(),requestDto.getPhoneNum())
                .orElseThrow(()-> new CustomException(ErrorStatus.USER_NOT_FOUND));
        return new FindLoginIdResponseDto(thisUser.getLoginId());
        }
    }

    // 소셜 로그인 및 회원가입
    // 소셜 로그인 -> 가입 안되어있을 경우 회원가입
    // 인증 코드를 가져오기
