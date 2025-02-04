package com.app.treen.user.service;

import com.app.treen.common.response.code.status.ErrorStatus;
import com.app.treen.common.response.exception.CustomException;
import com.app.treen.jpa.repository.user.UserRepository;
import com.app.treen.user.dto.request.CustomUserInfoDto;
import com.app.treen.user.dto.response.LoginResponseDto;
import com.app.treen.user.dto.response.OauthAccessTokenResponse;
import com.app.treen.user.dto.response.TokenResponseDto;
import com.app.treen.user.entity.OAuth2Attribute;
import com.app.treen.user.entity.RoleType;
import com.app.treen.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthService {

    // InMemoryClientRegistrationRepository는 더 이상 사용하지 않으므로 제거합니다.
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RestTemplate restTemplate;

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "Bearer ";

    @Value("${auth.kakao.member-info-request-uri}")
    private String kakaoMemberInfoRequestUri;
    @Value("${auth.google.member-info-request-uri}")
    private String googleMemberInfoRequestUri;
    @Value("${auth.naver.member-info-request-uri}")
    private String naverMemberInfoRequestUri;

    /**
     * SNS 서버로부터 엑세스 토큰을 전달받아 사용자 정보를 요청
     */
    private Map<String, Object> getUserAttributes(String provider, String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(AUTHORIZATION_HEADER, TOKEN_TYPE + accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object> request = new HttpEntity<>(headers);
            String requestUri;
            switch (provider) {
                case "kakao":
                    requestUri = kakaoMemberInfoRequestUri;
                    break;
                case "google":
                    requestUri = googleMemberInfoRequestUri;
                    break;
                case "naver":
                    requestUri = naverMemberInfoRequestUri;
                    break;
                default:
                    throw new CustomException(ErrorStatus.INVALID_PROVIDER);
            }
            // GET 요청을 보내 사용자 정보를 Map으로 받아옴
            return restTemplate.getForEntity(requestUri, Map.class, request).getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new CustomException(ErrorStatus.INVALID_OAUTH_TOKEN);
        }
    }

    /**
     * 사용자 저장 로직
     */
    public User saveUser(OAuth2Attribute oAuth2Attribute, String provider) {
        String loginId = oAuth2Attribute.getEmail();
        String profileImg = oAuth2Attribute.getPicture();
        return User.builder()
                .roles(Collections.singletonList(RoleType.USER))
                .profileImgUrl(profileImg)
                .loginId(loginId)
                .loginType(provider)
                .build();
    }

    /**
     * 로그인 및 회원가입 통합 처리
     *
     * 클라이언트가 SNS 서버로부터 받은 엑세스 토큰을 전달받아
     * 해당 토큰을 이용해 사용자 정보를 조회한 후,
     * DB에 저장(회원가입) 또는 기존 사용자라면 로그인 처리하고,
     * JwtProvider를 통해 자체 JWT 토큰을 발급
     */
    public LoginResponseDto login(String providerName, String accessToken) {
        // 1. 전달받은 accessToken을 사용하여 SNS 서버로부터 사용자 정보를 조회
        Map<String, Object> userAttribute = getUserAttributes(providerName, accessToken);

        // 2. provider별로 사용자 정보의 식별키(attributeKey)를 결정합니다.
        //    예를 들어, Google의 경우에는 "sub" 또는 "id"를, Kakao는 내부적으로 "email"을 사용하도록 매핑되어 있으므로...
        String attributeKey;
        switch (providerName) {
            case "google":
                // Google은 보통 "sub" 또는 "id"를 사용 (필요에 따라 수정)
                attributeKey = "sub";
                break;
            case "kakao":
                attributeKey = "email"; // OAuth2Attribute.ofKakao 내부에서 email을 사용함
                break;
            case "naver":
                attributeKey = "id";    // OAuth2Attribute.ofNaver 내부에서 id를 사용함
                break;
            default:
                throw new CustomException(ErrorStatus.INVALID_PROVIDER);
        }

        // 3. SNS 사용자 정보를 OAuth2Attribute 객체로 변환
        OAuth2Attribute attributes = OAuth2Attribute.of(providerName, attributeKey, userAttribute);

        // 4. DB에서 해당 이메일(로그인 ID)로 사용자 존재 여부 조회
        Optional<User> findUser = userRepository.findByLoginId(attributes.getEmail());
        User user;
        if (findUser.isPresent()) {
            user = findUser.get();
        } else {
            // 신규 사용자라면 저장
            user = saveUser(attributes, providerName);
        }

        // 5. 사용자 정보를 기반으로 자체 JWT 토큰 발급
        CustomUserInfoDto userInfoDto = new CustomUserInfoDto(user.getId(), user.getLoginId(), user.getRoles());
        TokenResponseDto jwtTokenResponse = jwtProvider.createTokenByLogin(userInfoDto);

        // 6. 사용자 정보와 JWT 토큰을 포함하는 응답 반환
        return new LoginResponseDto(user, jwtTokenResponse);
    }
}
