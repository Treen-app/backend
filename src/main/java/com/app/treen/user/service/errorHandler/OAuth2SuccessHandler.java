package com.app.treen.user.service.errorHandler;

import com.app.treen.jpa.repository.user.UserRepository;
import com.app.treen.user.dto.request.CustomUserInfoDto;
import com.app.treen.user.dto.response.TokenResponseDto;
import com.app.treen.user.entity.OAuth2Attribute;
import com.app.treen.user.entity.User;
import com.app.treen.user.service.JwtProvider;
import com.app.treen.user.service.OAuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final OAuthService oauthUserService;
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 2. provider 정보(예: kakao, google, naver)는 필요에 따라 request, session, 혹은 다른 방법으로 구분
        String provider = "kakao";

        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(provider, "id", attributes);

        // 4. 사용자 저장 또는 업데이트
        User user = oauthUserService.saveUser(oAuth2Attribute, provider);

        // 5. JWT 토큰 발급 (JwtProvider의 메서드를 통해 토큰 생성)
        TokenResponseDto tokenResponse = jwtProvider.createTokenByLogin(new CustomUserInfoDto(user.getId(), user.getLoginId(), user.getRoles()));

        // 6. 발급된 토큰을 클라이언트에 전달 (예: response 헤더에 추가하거나, 리다이렉트 URL에 포함)
        response.setHeader("Authorization", tokenResponse.getAccessToken());

        // 또는 리다이렉트 시 URL 파라미터로 전달하는 방식도 사용 가능
        // response.sendRedirect("/home?token=" + tokenResponse.getAccessToken());
    }
}
