package com.kboticketing.kboticketing.utils;

import java.security.SecureRandom;

/**
 * @author hazel
 */
public class NumberUtils {

    private static final int CODE_LENGTH = 6;

    public static String createRandomNumber() {
        StringBuilder code = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomNumber = secureRandom.nextInt(10); //0부터 9까지 랜덤한 정수 반환
            code.append(randomNumber);
        }
        return code.toString();
    }
}
