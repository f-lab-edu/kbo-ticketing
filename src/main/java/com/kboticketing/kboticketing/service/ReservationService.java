package com.kboticketing.kboticketing.service;

import com.kboticketing.kboticketing.dao.ReservationMapper;
import com.kboticketing.kboticketing.dao.SeatMapper;
import com.kboticketing.kboticketing.dto.ReservationDto;
import com.kboticketing.kboticketing.dto.SeatDto;
import com.kboticketing.kboticketing.exception.CustomException;
import com.kboticketing.kboticketing.exception.ErrorCode;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author hazel
 */
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final SeatMapper seatMapper;
    private final ReservationMapper reservationMapper;
    private final RedisTemplate<String, String> redisTemplate;


    public void reserveSeats(ReservationDto reservationDto, Integer userId) {
        LocalDateTime currentTime = LocalDateTime.now();
        int seatCnt = reservationDto.getSeats()
                                    .size();
        if (seatCnt == 0) {
            throw new CustomException(ErrorCode.SELECT_SEAT_FIRST);
        }

        String redisKey;
        for (SeatDto seatDto : reservationDto.getSeats()) {
            redisKey = String.format("%d_%d_%d", seatDto.getScheduleId(), seatDto.getSeatGradeId(),
                seatDto.getSeatNumber());
            String value = redisTemplate.opsForValue()
                                        .get(redisKey);

            if (value == null) {
                throw new CustomException(ErrorCode.SELECT_SEAT_FIRST);
            }

            Integer valueUserId = Integer.valueOf(value);
            if (!valueUserId.equals(userId)) {
                throw new CustomException(ErrorCode.NOT_MY_SEAT);
            }
        }

        //좌석등급 별 예매 가능 수량 확인
        SeatDto seatDto = reservationDto.getSeats()
                                        .get(0);
        Integer maxReservationNum = seatMapper.selectMaxReservationSeat(seatDto.getSeatGradeId(),
            seatDto.getSeatGradeId());
        if (seatCnt > maxReservationNum) {
            throw new CustomException(ErrorCode.MAXIMUM_SEAT_EXCEED);
        }

        // 최대 예매 가능 횟수 확인
        Boolean isReservationLimitExceeded = reservationMapper.checkReservationLimit(
            seatDto.getScheduleId());
        if (!isReservationLimitExceeded) {
            throw new CustomException(ErrorCode.MAXIMUM_RESERVATION_EXCEED);
        }

        //예매하기
        try {
            reservationMapper.insert(reservationDto, userId, currentTime);
        } catch (DuplicateKeyException e) {
            throw new CustomException(ErrorCode.RESERVED_SEAT);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.RESERVED_PROCESS_ERROR);
        }
    }
}
