package com.app.treen.common.response.exception;

import com.app.treen.common.response.code.status.ErrorStatus;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorStatus errorCode;

    public CustomException(ErrorStatus errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(ErrorStatus errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}