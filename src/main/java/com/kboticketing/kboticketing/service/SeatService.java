package com.kboticketing.kboticketing.service;

import com.kboticketing.kboticketing.domain.SeatGrade;
import com.kboticketing.kboticketing.dto.ReservationSeatDto;
import com.kboticketing.kboticketing.dao.SeatMapper;
import com.kboticketing.kboticketing.domain.Reservation;
import com.kboticketing.kboticketing.dto.SeatDto;
import com.kboticketing.kboticketing.utils.exception.CustomException;
import com.kboticketing.kboticketing.utils.exception.ErrorCode;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author hazel
 */
@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatMapper seatMapper;
    private final RedisTemplate<String, String> redisTemplate;

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

    public void selectSeats(SeatDto seatDto, Integer userId) {
        String redisKey = String.format("%d_%d_%d", seatDto.getScheduleId(),
            seatDto.getSeatGradeId(), seatDto.getSeatNumber());

        Boolean selected = redisTemplate.hasKey(redisKey);
        if (Boolean.TRUE.equals(selected)) {
            throw new CustomException(ErrorCode.SEAT_IN_PROGRESS);
        } else {
            redisTemplate.opsForValue()
                         .set(redisKey, String.valueOf(userId));
            redisTemplate.expire(redisKey, 7, TimeUnit.MINUTES);
        }
    }
}
