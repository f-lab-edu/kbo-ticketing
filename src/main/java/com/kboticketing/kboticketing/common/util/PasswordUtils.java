package com.kboticketing.kboticketing.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author hazel
 */
public class PasswordUtils {

    // todo bean으로 수정
    static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String encryptPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public static Boolean matchPassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
