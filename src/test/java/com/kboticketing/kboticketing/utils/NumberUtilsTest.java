package com.kboticketing.kboticketing.utils;

import static org.junit.jupiter.api.Assertions.*;

import com.kboticketing.kboticketing.common.util.NumberUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

/**
 * @author hazel
 */
class NumberUtilsTest {

    @RepeatedTest(10)
    @DisplayName("랜덤 넘버 생성 반복 테스트")
    void createRandomNumberRepeatTest() {
        int numberLength = 6;

        String randomNumber1 = NumberUtils.createRandomNumber();
        String randomNumber2 = NumberUtils.createRandomNumber();

        assertEquals(numberLength, randomNumber1.length());
        assertEquals(numberLength, randomNumber2.length());

        assertNotEquals(randomNumber1, randomNumber2);
    }
}