package com.kboticketing.kboticketing.utils.response;

import com.kboticketing.kboticketing.utils.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

/**
 * @author hazel
 */

@Getter
@Builder
public class ErrorResponse {
    private final int code;
    private final String message;
    private final long timestamp;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus()).
                body(ErrorResponse.builder().code(errorCode.getCode()).message(errorCode.getMessage()).timestamp(System.currentTimeMillis()).build());
    }


}
