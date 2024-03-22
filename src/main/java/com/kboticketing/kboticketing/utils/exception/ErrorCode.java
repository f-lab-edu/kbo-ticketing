package com.kboticketing.kboticketing.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author hazel
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_INPUT(HttpStatus.BAD_REQUEST, 4000, "입력값이 유효하지 않습니다");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
