package com.kboticketing.kboticketing.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hazel
 */
@Getter
@RequiredArgsConstructor
public class VerificationCodeDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 아닙니다.")
    private final String email;

    @NotBlank(message = "인증번호를 입력해주세요.")
    private final String verificationCode;
}
