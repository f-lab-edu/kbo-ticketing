package com.kboticketing.kboticketing.service;

import com.kboticketing.kboticketing.dao.ReservationMapper;
import com.kboticketing.kboticketing.dao.SeatMapper;
import com.kboticketing.kboticketing.dto.ReservationDto;
import com.kboticketing.kboticketing.dto.SeatDto;
import com.kboticketing.kboticketing.utils.exception.CustomException;
import com.kboticketing.kboticketing.utils.exception.ErrorCode;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * @author hazel
 */
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final SeatMapper seatMapper;
    private final ReservationMapper reservationMapper;

    public void reserveSeats(ReservationDto ReservationDto, Integer userId) {

        LocalDateTime currentTime = LocalDateTime.now();
        int seatCnt = ReservationDto.getSeats()
                                    .size();

        if (seatCnt == 0) {
            throw new CustomException(ErrorCode.SELECT_SEAT);
        }

        //좌석등급 별 예매 가능 수량 확인
        SeatDto seatDto = ReservationDto.getSeats()
                                        .get(0);
        Integer maxReservationNum = seatMapper.selectMaxReservationSeat(seatDto.getSeatGradeId(),
            seatDto.getSeatGradeId());
        if (seatCnt > maxReservationNum) {
            throw new CustomException(ErrorCode.MAXIMUM_SEAT_EXCEED);
        }

        // 최대 예매 가능 횟수 확인
        Boolean check = reservationMapper.checkReservationLimit(seatDto.getScheduleId());
        if (!check) {
            throw new CustomException(ErrorCode.MAXIMUM_RESERVATION_EXCEED);
        }

        //예매하기
        for (int i = 0; i < seatCnt; i++) {
            try {
                reservationMapper.insert(ReservationDto.getSeats()
                                                       .get(i), userId, currentTime);
            } catch (DuplicateKeyException e) {
                throw new CustomException(ErrorCode.RESERVED_SEAT);
            } catch (Exception e) {
                throw new CustomException(ErrorCode.RESERVED_PROCESS_ERROR);
            }
        }
    }
}
