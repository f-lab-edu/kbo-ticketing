package com.kboticketing.kboticketing.utils.exception;

import com.kboticketing.kboticketing.utils.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * @author hazel
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e,
        HttpServletRequest request) {

        log.error("url : {}, handleCustomException throw CustomException : {}, Error Message: {}",
            request.getRequestURI(), e.getErrorCode(), e.getErrorCode()
                                                        .getMessage());

        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
        MethodArgumentNotValidException e, HttpServletRequest request) {

        log.error("url : {},  handleValidationException throw CustomException : {}",
            request.getRequestURI(), e.getMessage());

        BindingResult bindingResult = e.getBindingResult();
        FieldError firstError = bindingResult.getFieldError();
        String defaultMessage = firstError.getDefaultMessage();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                             body(ErrorResponse.builder()
                                               .message(defaultMessage)
                                               .timestamp(now.format(formatter))
                                               .build());
    }
}
