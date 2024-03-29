package com.kboticketing.kboticketing.service;

import com.kboticketing.kboticketing.dao.UserMapper;
import com.kboticketing.kboticketing.domain.User;
import com.kboticketing.kboticketing.dto.EmailRequestDto;
import com.kboticketing.kboticketing.dto.UserDto;
import com.kboticketing.kboticketing.utils.EmailUtils;
import com.kboticketing.kboticketing.utils.exception.CustomException;
import com.kboticketing.kboticketing.utils.exception.ErrorCode;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import static com.kboticketing.kboticketing.utils.NumberUtils.createRandomNumber;
import static com.kboticketing.kboticketing.utils.exception.ErrorCode.EMAIL_ALREADY_EXISTS;
import static com.kboticketing.kboticketing.utils.exception.ErrorCode.PASSWORD_MISMATCH;

/**
 * @author hazel
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserMapper userMapper;
    private final EmailUtils emailUtil;
    private final RedisTemplate<String, String> redisTemplate;

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

    public void sendVerificationCode(EmailRequestDto emailRequestDto) {
        Boolean hasKey = redisTemplate.hasKey(emailRequestDto.getEmail());
        if (hasKey) {
            throw new CustomException(ErrorCode.FREQUENT_VERIFICATION_REQUEST);
        }

        String verificationCode = createRandomNumber();
        log.info("{}'s verification code  = {}", emailRequestDto.getEmail(), verificationCode);

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(emailRequestDto.getEmail(), verificationCode, 5,
            TimeUnit.MINUTES);

        emailUtil.sendEmail(verificationCode, emailRequestDto.getEmail());
    }
}


