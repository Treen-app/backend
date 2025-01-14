package com.app.treen.common.response.code;

import com.app.treen.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<?>> handleBaseException(BaseException ex) {
        BaseErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.onFailure(
                        errorCode.getReasonHttpStatus().getCode(),
                        errorCode.getReasonHttpStatus().getMessage(),
                        null
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
        return ResponseEntity
                .status(500)
                .body(ApiResponse.onFailure(
                        "COMMON500",
                        "An unexpected error occurred: " + ex.getMessage(),
                        null
                ));
    }
}