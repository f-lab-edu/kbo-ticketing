package com.kboticketing.kboticketing.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author hazel
 */
public class DateTimeUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd HH:mm:ss");

    public static String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }

}
