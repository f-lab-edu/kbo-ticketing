package com.kboticketing.kboticketing.utils.exception;

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
