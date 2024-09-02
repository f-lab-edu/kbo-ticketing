package com.kboticketing.kboticketing.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hazel
 */
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;
}
