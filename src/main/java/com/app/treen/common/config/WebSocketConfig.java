package com.app.treen.common.config;

import com.app.treen.common.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    /**
     * 클라이언트에서 WebSocket에 접속할 수 있는 endpoint를 지정
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // stomp 접속 주소 url = ws://localhost:8080/ws, 프로토콜이 http가 아니다!
        registry.addEndpoint("/ws") // 연결될 엔드포인트
                .setAllowedOrigins("http://localhost:3000").withSockJS();
    }

    /**
     * 메시지를 중간에서 라우팅할 때 사용하는 메시지 브로커를 구성
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지를 구독(수신)하는 요청 엔드포인트, sub하는 클라이언트에게 메시지 전달
        registry.enableSimpleBroker("/sub");

        // 메시지를 발행(송신)하는 엔드포인트, 클라이언트의 send 요청 처리
        registry.setApplicationDestinationPrefixes("/pub");
    }

    /**
     * 사용자가 웹 소켓 연결에 연결 될 때와 끊길 때 추가기능(인증, 세션 관리 등)을 위한 인터셉터
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }

}
