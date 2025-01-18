package com.app.treen.common.response.code.status;

import com.app.treen.common.response.code.BaseCode;
import com.app.treen.common.response.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 일반적인 응답
    _OK(HttpStatus.OK, "COMMON200", "성공입니다."),

    // 멤버 관련 응답
    JOIN_SUCCESS(HttpStatus.OK, "ACCOUNT200", "회원가입에 성공하였습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "ACCOUNT200", "로그인에 성공하였습니다."),

    // ~~~ 관련 응답
    PIN_LIKE(HttpStatus.OK, "LIKE200", "좋아요 등록에 성공하였습니다."),
    PIN_UNLIKE(HttpStatus.OK, "LIKE201", "좋아요 취소를 성공하였습니다."),

    // 상품 응답
    PRODUCT_EDIT_SUCCESS(HttpStatus.OK, "UPDATE200", "상품 수정을 성공하였습니다."),
    PRODUCT_SAVE_SUCCESS(HttpStatus.OK, "SAVE200", "상품 등록에 성공하였습니다."),
    PRODUCT_DELETE_SUCCESS(HttpStatus.OK, "DELETE200", "상품 삭제에 성공하였습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
