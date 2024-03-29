package com.kboticketing.kboticketing.utils.response;

import com.kboticketing.kboticketing.utils.DateTimeUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hazel
 */

@RequiredArgsConstructor
@Getter
public class CommonResponse {

    private final Object data;
    private final String timestamp;

    public static CommonResponse ok(Object data) {
        return new CommonResponse(data, DateTimeUtils.getCurrentTime());
    }
}
