package com.kboticketing.kboticketing.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import com.kboticketing.kboticketing.dao.SeatMapper;
import com.kboticketing.kboticketing.domain.Reservation;
import com.kboticketing.kboticketing.domain.SeatGrade;
import com.kboticketing.kboticketing.dto.ReservationSeatDto;
import com.kboticketing.kboticketing.dto.SeatDto;
import com.kboticketing.kboticketing.utils.exception.CustomException;
import com.kboticketing.kboticketing.utils.exception.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author hazel
 */
@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @Mock
    private SeatMapper seatMapper;

    @InjectMocks
    private SeatService seatService;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Test
    @DisplayName("[SUCCESS] 예약 좌석 목록 조회 테스트")
    public void getSeatsByGradeTest() {

        //given
        ArrayList<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(1));
        reservations.add(new Reservation(2));
        given(seatMapper.selectReservations(any(), any()))
            .willReturn(reservations);

        ArrayList<Integer> reservedSeat = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservedSeat.add(reservation.getSeatNumber());
        }

        ReservationSeatDto reservationSeatDto = new ReservationSeatDto(reservedSeat);

        //when, then
        assertThat(seatService.getSeatsByGrade(any(), any())).usingRecursiveAssertion()
                                                             .isEqualTo(reservationSeatDto);
    }


    @Test
    @DisplayName("[SUCCESS]좌석 정보 조회 테스트")
    public void getSeatGradeTest() {

        //given
        SeatGrade seatGrade = new SeatGrade(1, "블루석 300블럭", "300");
        given(seatMapper.selectSeatGrade(any())).willReturn(seatGrade);

        //when,then
        assertThat(seatService.getSeatGrade(any())).usingRecursiveAssertion()
                                                   .isEqualTo(seatGrade);
    }

    @Test
    @DisplayName("[FAIL] 좌석 선택 테스트")
    public void inProgressTest() {

        //given - 이미 좌석이 선택되었다고 가정
        SeatDto seatDto = new SeatDto(1, 1, 1);
        given(redisTemplate.execute(any(), anyList(), any())).willReturn("SEAT_IN_PROGRESS");

        //when
        CustomException customException = assertThrows(CustomException.class, () -> {
            seatService.selectSeats(seatDto, 1);
        });

        //then
        assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.SEAT_IN_PROGRESS);
    }

    @Test
    @DisplayName("[SUCCESS]좌석 선택 성공 테스트")
    public void selectSeatTest() {

        //given
        SeatDto seatDto = new SeatDto(1, 1, 1);
        String redisKey = String.format("%d_%d_%d", seatDto.getScheduleId(),
            seatDto.getSeatGradeId(), seatDto.getSeatNumber());

        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(redisTemplate.hasKey(any())).willReturn(false);

        //when
        assertDoesNotThrow(() -> seatService.selectSeats(seatDto, anyInt()));

        // then
        verify(redisTemplate, times(1)).hasKey(redisKey);
        verify(redisTemplate, times(1)).expire(eq(redisKey), eq(7L), eq(TimeUnit.MINUTES));
    }
}