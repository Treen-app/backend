package com.app.treen.global;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * 2000 : 요청 성공
     */
    SUCCESS(true, 2000, "요청에 성공하였습니다."),


    /**
     * 4000 : Request
     */
    // Common
    PASSWORD_NOT_MATCH(false, 4000, "Wrong Password"),
    LOGIN_EXPIRED(false,4001, "인증이 만료되었습니다. 다시 로그인해주세요."),
    USER_NOT_EXISTS(false,4002,"회원 정보가 존재하지 않습니다.");





    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
