package com.app.treen.common.handler;

import com.app.treen.common.redis.chat_room_connection.service.ChatRoomConnectionService;
import com.app.treen.common.response.code.status.ErrorStatus;
import com.app.treen.common.response.exception.CustomException;
import com.app.treen.user.service.CustomUserDetails;
import com.app.treen.user.service.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;
    private final ChatRoomConnectionService chatRoomConnectionService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // websocket Authorization 헤더 위치는 http와 다르기 때문에 추가 구현
        if (accessor.getCommand() == StompCommand.CONNECT) {
            if (!jwtProvider.validateToken(accessor.getFirstNativeHeader("Authorization"))) {
                throw new CustomException(ErrorStatus.JWT_INVALID);
            }
            log.info("StompUser = {}, Authentication = {}", accessor.getUser(), SecurityContextHolder.getContext().getAuthentication());
        } else if (accessor.getCommand() == StompCommand.SUBSCRIBE
                && "enter".equals(accessor.getFirstNativeHeader("action"))) {

            // TODO 추후 redis 도입 고려
            Long chatRoomId = Optional.ofNullable(getChatRoomId(accessor.getDestination()))
                    .orElseThrow(() -> new CustomException(ErrorStatus.NOT_VALID_URI));
            Long loginMemberId = getLoginMemberId(accessor.getFirstNativeHeader("Authorization"));

            chatRoomConnectionService.connect(chatRoomId, loginMemberId);
        } else if (accessor.getCommand() == StompCommand.DISCONNECT) {

            Long chatRoomId = Optional.ofNullable(getChatRoomId(accessor.getDestination()))
                    .orElseThrow(() -> new CustomException(ErrorStatus.NOT_VALID_URI));
            Long loginMemberId = getLoginMemberId(accessor.getFirstNativeHeader("Authorization"));

            chatRoomConnectionService.disconnect(chatRoomId, loginMemberId);
        }

        return message;
    }

    private Long getChatRoomId(String destination) {
        if (Strings.isBlank(destination)) {
            return null;
        }

        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex == -1) {
            return null;
        }

        try {
            return Long.parseLong(destination.substring(lastIndex + 1));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String getAccessToken(StompHeaderAccessor accessor) {
        String authorizationHeader = accessor.getFirstNativeHeader("Authorization");
        if (authorizationHeader == null) {
            log.info("chat header가 없는 요청입니다.");
            throw new MalformedJwtException("jwt");
        }

        return authorizationHeader.replace("Bearer ", "");
    }

    private Long getLoginMemberId(String token) {
        if (!jwtProvider.validateToken(token)) {
            throw new CustomException(ErrorStatus.JWT_INVALID);
        }

        Claims claims = jwtProvider.getUserInfoFromToken(token);
        String loginId = claims.get("loginId", String.class);

        UsernamePasswordAuthenticationToken authentication = jwtProvider.createUserAuthentication(loginId);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return userDetails.getUser().getId();
    }

}
