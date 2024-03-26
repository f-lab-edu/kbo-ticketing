package com.kboticketing.kboticketing.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * @author hazel
 */
@Getter
public class EmailRequestDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 아닙니다.")
    private final String email;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public EmailRequestDto(String email) {
        this.email = email;
    }
}
