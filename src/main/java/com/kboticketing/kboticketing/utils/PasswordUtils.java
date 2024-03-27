package com.kboticketing.kboticketing.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author hazel
 */
public class PasswordUtils {

    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String encryptPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
