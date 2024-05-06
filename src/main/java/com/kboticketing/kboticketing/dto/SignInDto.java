package com.kboticketing.kboticketing.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hazel
 */
@Getter
@RequiredArgsConstructor
public class SignInDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 아닙니다.")
    private final String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,20}", message = "비밀번호는 영문자와 숫자,특수문자가 최소 1개 이상씩 포함된 8~20자로 입력해주세요.")
    private final String password;
}
