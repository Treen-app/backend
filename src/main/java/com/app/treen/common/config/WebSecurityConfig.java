package com.app.treen.common.config;


import com.app.treen.user.service.errorHandler.CustomAccessDeniedHandler;
import com.app.treen.user.service.errorHandler.CustomAuthenticationEntryPoint;
import com.app.treen.user.service.filter.JwtAuthFilter;
import com.app.treen.user.service.filter.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableScheduling
public class WebSecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final JwtAuthFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;



    // 비밀번호 암호화 방식 설정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 보안 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()); // CSRF 비활성화 (토큰 기반 인증이므로)
        http    .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS)) // 세션 상태 비활성화
                .exceptionHandling((exceptionsConfig)
                        ->   exceptionsConfig.authenticationEntryPoint(customAuthenticationEntryPoint) // 인증 실패 처리
                        .accessDeniedHandler(customAccessDeniedHandler) // 권한 실패 처리
                );
        http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        // 인증 없이 접근 가능한 경로
                        .requestMatchers(
                                "/api/**",
                                "/swagger-ui/**", // Swagger UI
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        //  인증이 필요한 경로 (JWT 필요)
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 페이지 권한 설정
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                );
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionFilter,jwtAuthenticationFilter.getClass());

        return http.build();

    }

    // CORS 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
        configuration.setAllowCredentials(true); // 인증 정보 포함 요청 허용
        configuration.addAllowedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 정책 적용

        return source;
    }


}