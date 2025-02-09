package com.app.treen.user.service;

import com.app.treen.common.response.code.status.ErrorStatus;
import com.app.treen.common.response.exception.CustomException;
import com.app.treen.jpa.repository.user.UserRepository;
import com.app.treen.user.dto.request.CustomUserInfoDto;
import com.app.treen.user.dto.request.OAuthTokenRequestDto;
import com.app.treen.user.dto.response.GoogleOAuthTokenResponse;
import com.app.treen.user.dto.response.LoginResponseDto;
import com.app.treen.user.dto.response.OauthAccessTokenResponse;
import com.app.treen.user.dto.response.TokenResponseDto;
import com.app.treen.user.entity.OAuth2Attribute;
import com.app.treen.user.entity.RoleType;
import com.app.treen.user.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RestTemplate restTemplate;

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "Bearer ";

    @Value("${auth.naver.redirect-uri}")
    private String naverRedirectUri;

    @Value("${auth.naver.client-id}")
    private String naverClientId;

    @Value("${auth.naver.token-request-uri}")
    private String naverTokenRequestUri;
    @Value("${auth.naver.client-secret}")
    private String naverClientSecret;

    @Value("${auth.google.redirect-uri}")
    private String googleRedirectUri;

    @Value("${auth.google.client-id}")
    private String googleClientId;

    @Value("${auth.google.client-secret}")
    private String googleClientSecret;

    @Value("${auth.google.token-request-uri}")
    private String googleTokenRequestUri;

    @Value("${auth.kakao.member-info-request-uri}")
    private String kakaoMemberInfoRequestUri;
    @Value("${auth.google.member-info-request-uri}")
    private String googleMemberInfoRequestUri;
    @Value("${auth.naver.member-info-request-uri}")
    private String naverMemberInfoRequestUri;

    /**
     * Google 토큰 받아오기
     * @param code
     * @return GoogleOauthTokenResponse
     */
    public GoogleOAuthTokenResponse getGoogleToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", googleClientId);
        params.add("client_secret", googleClientSecret);
        params.add("redirect_uri", googleRedirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(googleTokenRequestUri, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(response.getBody(), GoogleOAuthTokenResponse.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorStatus.INVALID_OAUTH_TOKEN);
        }

        throw new CustomException(ErrorStatus.INVALID_OAUTH_TOKEN);
    }


    /**
     * 네이버 토큰 받아오기
     * @param code
     * @return
     */
    public OauthAccessTokenResponse getNaverToken(String code, String state) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", naverClientId);
        params.add("code", code);
        params.add("client_secret", naverClientSecret);
        params.add("state", state);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(naverTokenRequestUri, HttpMethod.POST,request, String.class);
        log.info("응답 " + response);
        log.info("네이버 OAuth 요청 - body: {}", request);
        if (response.getStatusCode() == HttpStatus.OK){
            ObjectMapper objectMapper = new ObjectMapper();
            OauthAccessTokenResponse accessTokenResponse = null;
            try {
                accessTokenResponse = objectMapper.readValue(response.getBody(), OauthAccessTokenResponse.class);
                return accessTokenResponse;
            }  catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        throw new CustomException(ErrorStatus.INVALID_OAUTH_TOKEN);
    }

    /**
     * SNS 서버로부터 엑세스 토큰을 전달받아 사용자 정보를 요청
     */
    private Map<String, Object> getUserAttributes(String provider, String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            String requestUri;

            if (provider.equals("naver")) {
                headers.set(AUTHORIZATION_HEADER, TOKEN_TYPE + accessToken);
                headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
                requestUri = naverMemberInfoRequestUri;
            } else if (provider.equals("kakao")) {
                headers.set(AUTHORIZATION_HEADER, TOKEN_TYPE + accessToken);
                headers.setContentType(new MediaType("application", "x-www-form-urlencoded", StandardCharsets.UTF_8));
                requestUri = kakaoMemberInfoRequestUri;
            } else if (provider.equals("google")) {
                headers.set(AUTHORIZATION_HEADER, TOKEN_TYPE + accessToken);
                headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
                requestUri = googleMemberInfoRequestUri;
            } else {
                throw new CustomException(ErrorStatus.INVALID_PROVIDER);
            }

            log.info("Request URI: " + requestUri);
            log.info("Request Headers: " + headers.toString());

            // GET 요청을 보내 사용자 정보를 Map으로 받아옴.
            HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
            return restTemplate.exchange(requestUri, HttpMethod.GET, requestEntity, Map.class).getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            e.printStackTrace();
            throw new CustomException(ErrorStatus.INVALID_OAUTH_TOKEN);
        }
    }


    /**
     * 사용자 저장 로직
     */
    public User saveUser(OAuth2Attribute oAuth2Attribute, String provider) {
        String loginId = oAuth2Attribute.getEmail();
        String profileImg = oAuth2Attribute.getPicture();
        String phoneNum = (oAuth2Attribute.getPhoneNum() != null) ? oAuth2Attribute.getPhoneNum() : "000000000";

        log.info("loginId : " + loginId);
        log.info("phoneNum : " + phoneNum);

        return User.builder()
                .roles(Collections.singletonList(RoleType.USER))
                .profileImgUrl(profileImg)
                .loginId(loginId)
                .userName(loginId)
                .phoneNum(phoneNum) // null 체크 후 저장
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
        log.info("사용자 정보를 조회합니다. " + userAttribute.toString());
        // 2. provider별로 사용자 정보의 식별키(attributeKey)를 결정합니다.
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
            log.info("사용자가 없습니다.");
        } else {
            // 신규 사용자라면 저장
            log.info("새로운 사용자를 생성합니다.");
            user = saveUser(attributes, providerName);
            userRepository.save(user);
            log.info(user.getId().toString());
        }

        // 5. 사용자 정보를 기반으로 자체 JWT 토큰 발급
        CustomUserInfoDto userInfoDto = new CustomUserInfoDto(user.getId(), user.getLoginId(), user.getRoles());
        TokenResponseDto jwtTokenResponse = jwtProvider.createTokenByLogin(userInfoDto);

        // 6. 사용자 정보와 JWT 토큰을 포함하는 응답 반환
        return new LoginResponseDto(user, jwtTokenResponse);
    }
}
