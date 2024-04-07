package com.kboticketing.kboticketing.service;

import com.kboticketing.kboticketing.domain.SeatGrade;
import com.kboticketing.kboticketing.dto.ReservationSeatDto;
import com.kboticketing.kboticketing.dao.SeatMapper;
import com.kboticketing.kboticketing.domain.Reservation;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author hazel
 */
@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatMapper seatMapper;

    public ReservationSeatDto getSeatsByGrade(String scheduleId, String seatGradeId) {

        ArrayList<Reservation> reservations = seatMapper.selectReservations(scheduleId,
            seatGradeId);

        ArrayList<Integer> reservedSeat = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservedSeat.add(reservation.getSeatNumber());
        }

        return new ReservationSeatDto(reservedSeat);
    }

    public SeatGrade getSeatGrade(String id) {
        return seatMapper.selectSeatGrade(id);
    }
}
