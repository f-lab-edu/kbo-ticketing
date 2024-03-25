package com.kboticketing.kboticketing.service;

import com.kboticketing.kboticketing.dao.UserMapper;
import com.kboticketing.kboticketing.domain.User;
import com.kboticketing.kboticketing.dto.UserDto;
import com.kboticketing.kboticketing.utils.enums.Role;
import com.kboticketing.kboticketing.utils.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.kboticketing.kboticketing.utils.exception.ErrorCode.EMAIL_ALREADY_EXISTS;
import static com.kboticketing.kboticketing.utils.exception.ErrorCode.PASSWORD_MISMATCH;

/**
 * @author hazel
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public void signUp(UserDto userDto) {
        //todo 인증번호 확인 로직 -> 인증번호 API 작업 후 작업 예정

        if (!userDto.getPassword()
                    .equals(userDto.getConfirmedPassword())) {
            throw new CustomException(PASSWORD_MISMATCH);
        }

        User registeredUser = userMapper.selectByEmail(userDto.getEmail());
        if (registeredUser != null) {
            throw new CustomException(EMAIL_ALREADY_EXISTS);
        }

        User user = userDto.toUser();
        userMapper.insert(user);
    }
}


