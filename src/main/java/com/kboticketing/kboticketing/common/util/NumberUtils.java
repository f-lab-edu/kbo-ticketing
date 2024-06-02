package com.kboticketing.kboticketing.common.util;

import java.security.SecureRandom;

/**
 * @author hazel
 */
public class NumberUtils {

    private static final int CODE_LENGTH = 6;
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String createRandomNumber() {
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomNumber = secureRandom.nextInt(10); //0부터 9까지 랜덤한 정수 반환
            code.append(randomNumber);
        }
        return code.toString();
    }
}
