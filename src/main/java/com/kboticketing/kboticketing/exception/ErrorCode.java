package com.kboticketing.kboticketing.exception;

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
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않습니다"),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "두 비밀번호가 일치하지 않습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    FREQUENT_VERIFICATION_REQUEST(HttpStatus.BAD_REQUEST, "잦은 인증번호 요청은 불가능합니다."),
    REQUEST_VERIFICATION_FIRST(HttpStatus.BAD_REQUEST, "인증을 먼저 진행해주세요."),
    WRONG_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "인증번호가 다릅니다."),
    SEAT_IN_PROGRESS(HttpStatus.BAD_REQUEST, "이미 선택된 좌석입니다."),
    SELECT_SEAT_FIRST(HttpStatus.BAD_REQUEST, "좌석를 선택해주세요."),
    NOT_MY_SEAT(HttpStatus.BAD_REQUEST, "내가 선택한 좌석이 아닙니다."),
    MAXIMUM_SEAT_EXCEED(HttpStatus.BAD_REQUEST, "예매 가능한 좌석 수량을 초과했습니다."),
    MAXIMUM_RESERVATION_EXCEED(HttpStatus.BAD_REQUEST, "한 경기당 예매 가능한 횟수를 초과했습니다."),
    RESERVED_SEAT(HttpStatus.BAD_REQUEST, "이미 예매된 좌석입니다."),
    LOGIN_REQUIRED(HttpStatus.BAD_REQUEST, "로그인을 먼저 진행해주세요."),

    /* 500 INTERNAL_SERVER_ERROR : 서버 오류 */
    FAIL_SEND_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송에 실패하였습니다."),
    RESERVED_PROCESS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예매 중 문제가 발생하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}