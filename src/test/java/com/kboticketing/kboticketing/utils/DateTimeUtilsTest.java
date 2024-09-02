package com.kboticketing.kboticketing.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.kboticketing.kboticketing.common.util.DateTimeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author hazel
 */
public class DateTimeUtilsTest {

    @Test
    @DisplayName("[SUCCESS] 시간 formatter 테스트")
    public void getCurrentTimeTest() {

        //when
        String currentTime = DateTimeUtils.getCurrentTime();

        //then
        assertNotNull(currentTime);
    }
}
