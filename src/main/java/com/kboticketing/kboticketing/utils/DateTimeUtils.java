package com.kboticketing.kboticketing.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author hazel
 */
public class DateTimeUtils {

    public static String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

}
