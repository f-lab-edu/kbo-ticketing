package com.kboticketing.kboticketing.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import lombok.Getter;

/**
 * @author hazel
 */
@Getter
public class ReservationDto {

    private final ArrayList<SeatDto> seats;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ReservationDto(ArrayList<SeatDto> seats) {
        this.seats = seats;
    }
}
