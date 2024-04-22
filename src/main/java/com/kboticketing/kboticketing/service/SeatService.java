package com.kboticketing.kboticketing.service;

import com.kboticketing.kboticketing.domain.SeatGrade;
import com.kboticketing.kboticketing.dto.ReservationSeatDto;
import com.kboticketing.kboticketing.dao.SeatMapper;
import com.kboticketing.kboticketing.domain.Reservation;
import com.kboticketing.kboticketing.dto.SeatDto;
import com.kboticketing.kboticketing.utils.exception.CustomException;
import com.kboticketing.kboticketing.utils.exception.ErrorCode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
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

        String script = "local redisKey = KEYS[1]" +
            "local userId = ARGV[1]" +
            "if redis.call('EXISTS', redisKey) == 1 then" +
            "    return 'SEAT_IN_PROGRESS'" +
            "else" +
            "    redis.call('SET', redisKey, userId)" +
            "    redis.call('EXPIRE', redisKey, 420)" +
            "    return 'SUCCESS'" +
            "end";

        List<String> keys = Collections.singletonList(redisKey);
        String result = redisTemplate.execute(
            new DefaultRedisScript<>(script, String.class), keys, String.valueOf(userId));

        if ("SEAT_IN_PROGRESS".equals(result)) {
            throw new CustomException(ErrorCode.SEAT_IN_PROGRESS);
        }
    }
}
