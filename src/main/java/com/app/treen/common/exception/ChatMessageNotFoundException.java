package com.app.treen.common.exception;

import lombok.Getter;

/**
 * 채팅 메시지를 찾을 수 없을 때 발생하는 사용자 정의 예외 클래스.
 */
@Getter
public class ChatMessageNotFoundException extends RuntimeException {

    private final String errorMessage;

    // 기본 생성자
    public ChatMessageNotFoundException() {
        super("채팅 메시지를 찾을 수 없습니다.");
        this.errorMessage = "채팅 메시지를 찾을 수 없습니다.";
    }

    // 사용자 정의 메시지를 받는 생성자
    public ChatMessageNotFoundException(String message) {
        super(message);
        this.errorMessage = message;
    }

    // 사용자 정의 메시지와 원인 예외를 받는 생성자
    public ChatMessageNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
    }

    // 원인 예외를 받는 생성자
    public ChatMessageNotFoundException(Throwable cause) {
        super("채팅 메시지를 찾을 수 없습니다.", cause);
        this.errorMessage = "채팅 메시지를 찾을 수 없습니다.";
    }
}
