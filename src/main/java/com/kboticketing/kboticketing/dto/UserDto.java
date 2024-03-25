package com.kboticketing.kboticketing.dto;

import com.kboticketing.kboticketing.domain.User;
import com.kboticketing.kboticketing.utils.PasswordUtils;
import com.kboticketing.kboticketing.utils.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import lombok.Getter;


/**
 * @author hazel
 */

@Getter
public final class UserDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private final String name;
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 아닙니다.")
    private final String email;
    @NotBlank(message = "인증코드를 입력해주세요.")
    private final String code;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,20}", message = "비밀번호는 영문자와 숫자,특수문자가 최소 1개 이상씩 포함된 8~20자로 입력해주세요.")
    private final String password;
    @NotBlank(message = "확인할 비밀번호를 입력해주세요.")
    private final String confirmedPassword;

    public UserDto(String name, String email, String code, String password,
        String confirmedPassword) {
        this.name = name;
        this.email = email;
        this.code = code;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
    }

    public User toUser() {
        String encryptedPassword = PasswordUtils.encryptPassword(password);
        return new User(name, email, encryptedPassword, Role.USER, LocalDateTime.now());
    }

}
