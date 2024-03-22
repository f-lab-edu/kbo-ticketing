package com.kboticketing.kboticketing.utils.response;

import com.kboticketing.kboticketing.utils.exception.ErrorCode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return ResponseEntity.status(errorCode.getHttpStatus())
                             .body(ErrorResponse.builder()
                                                .message(errorCode.getMessage())
                                                .timestamp(now.format(formatter))
                                                .build());
    }
}
