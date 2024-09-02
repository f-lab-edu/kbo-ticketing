package com.kboticketing.kboticketing.common.response;

import com.kboticketing.kboticketing.exception.ErrorCode;
import com.kboticketing.kboticketing.common.util.DateTimeUtils;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

/**
 * @author hazel
 */
@Getter
@Builder
public class ErrorResponse {

    private final String message;
    private final String timestamp;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {

        return ResponseEntity.status(errorCode.getHttpStatus())
                             .body(ErrorResponse.builder()
                                                .message(errorCode.getMessage())
                                                .timestamp(DateTimeUtils.getCurrentTime())
                                                .build());
    }
}
