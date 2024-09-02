package com.kboticketing.kboticketing.dto;

import java.util.ArrayList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hazel
 */

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class ReservationSeatDto {

    private final ArrayList<Integer> seatNumbers;

}
