package com.app.treen.common.response.code;

import org.springframework.http.HttpStatus;
public interface BaseErrorCode {

    String getMessage();
    String getCode();
    HttpStatus getHttpStatus();
    public ErrorReasonDTO getReason();
    public ErrorReasonDTO getReasonHttpStatus();

}
