package com.kboticketing.kboticketing.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

import com.kboticketing.kboticketing.dao.ReservationMapper;
import com.kboticketing.kboticketing.dao.SeatMapper;
import com.kboticketing.kboticketing.dto.ReservationDto;
import com.kboticketing.kboticketing.dto.SeatDto;
import com.kboticketing.kboticketing.exception.CustomException;
import com.kboticketing.kboticketing.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author hazel
 */

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    ReservationMapper reservationMapper;
    @Mock
    SeatMapper seatMapper;
    @InjectMocks
    ReservationService reservationService;
    @Mock
    private RedisTemplate<String, String> redisTemplate;
    @Mock
    private ValueOperations<String, String> valueOperations;

    @Test
    @DisplayName("[SUCCESS] 예매 테스트")
    public void reserveSeatsTest() throws Exception {

        //given
        ArrayList<SeatDto> seatArr = new ArrayList<>();
        SeatDto seat1 = new SeatDto(1, 1, 1);
        SeatDto seat2 = new SeatDto(1, 1, 2);
        seatArr.add(seat1);
        seatArr.add(seat2);
        ReservationDto reservationDto = new ReservationDto(seatArr);

        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(redisTemplate.opsForValue()
                           .get(any())).willReturn("1");
        given(seatMapper.selectMaxReservationSeat(anyInt(),
            anyInt())).willReturn(3);
        given(reservationMapper.checkReservationLimit(any())).willReturn(true);
        given(reservationMapper.insert(any(ReservationDto.class), anyInt(),
            any(LocalDateTime.class))).willReturn(null);

        //when, then
        assertDoesNotThrow(() -> reservationService.reserveSeats(reservationDto, 1));
    }

    @Test
    @DisplayName("[FAIL] 좌석 미선택 예매")
    public void unselectedSeatTest() {

        //given
        ArrayList<SeatDto> seatArr = new ArrayList<>();
        ReservationDto reservationDto = new ReservationDto(seatArr);

        // when
        CustomException customException = assertThrows(CustomException.class,
            () -> reservationService.reserveSeats(reservationDto, 1));

        //then
        assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.SELECT_SEAT_FIRST);
    }

    @Test
    @DisplayName("[FAIL] 좌석 개수 초과 예매")
    public void exceedMaximumSeatTest() {

        //given
        ArrayList<SeatDto> seatArr = new ArrayList<>();
        SeatDto seat1 = new SeatDto(1, 1, 1);
        SeatDto seat2 = new SeatDto(1, 1, 2);
        seatArr.add(seat1);
        seatArr.add(seat2);
        ReservationDto reservationDto = new ReservationDto(seatArr);

        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(redisTemplate.opsForValue()
                           .get(any())).willReturn("1");
        given(seatMapper.selectMaxReservationSeat(anyInt(), anyInt())).willReturn(1);

        //when
        CustomException customException = assertThrows(CustomException.class,
            () -> reservationService.reserveSeats(reservationDto, 1));

        //then
        assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.MAXIMUM_SEAT_EXCEED);
    }

    @Test
    @DisplayName("[FAIL] 예매 제한 초과 예매")
    public void exceedMaximumReservationTest() {

        //given
        ArrayList<SeatDto> seatArr = new ArrayList<>();
        SeatDto seat1 = new SeatDto(1, 1, 1);
        SeatDto seat2 = new SeatDto(1, 1, 2);
        seatArr.add(seat1);
        seatArr.add(seat2);
        ReservationDto reservationDto = new ReservationDto(seatArr);

        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(redisTemplate.opsForValue()
                           .get(any())).willReturn("1");
        given(seatMapper.selectMaxReservationSeat(anyInt(), anyInt())).willReturn(1);
        given(seatMapper.selectMaxReservationSeat(anyInt(),
            anyInt())).willReturn(3);
        given(reservationMapper.checkReservationLimit(any())).willReturn(false);

        //when
        CustomException customException = assertThrows(CustomException.class,
            () -> reservationService.reserveSeats(reservationDto, 1));

        //then
        assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.MAXIMUM_RESERVATION_EXCEED);
    }

    @Test
    @DisplayName("[FAIL] 예매 완료된 좌석 예매")
    public void reservedSeatTest() throws Exception {

        //given
        ArrayList<SeatDto> seatArr = new ArrayList<>();
        SeatDto seat1 = new SeatDto(1, 1, 1);
        SeatDto seat2 = new SeatDto(1, 1, 2);
        seatArr.add(seat1);
        seatArr.add(seat2);
        ReservationDto reservationDto = new ReservationDto(seatArr);

        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(redisTemplate.opsForValue()
                           .get(any())).willReturn("1");
        given(seatMapper.selectMaxReservationSeat(anyInt(), anyInt())).willReturn(1);
        given(seatMapper.selectMaxReservationSeat(anyInt(),
            anyInt())).willReturn(3);
        given(reservationMapper.checkReservationLimit(any())).willReturn(true);
        given(
            reservationMapper.insert(any(ReservationDto.class), anyInt(), any(LocalDateTime.class)))
            .willThrow(DuplicateKeyException.class);

        //when
        CustomException customException = assertThrows(CustomException.class,
            () -> reservationService.reserveSeats(reservationDto, 1));

        //then
        assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.RESERVED_SEAT);
    }

    @Test
    @DisplayName("[FAIL] 타인 좌석 에매")
    public void checkMySelectSeatTest() {

        //given
        ArrayList<SeatDto> seatArr = new ArrayList<>();
        SeatDto seat1 = new SeatDto(1, 1, 1);
        SeatDto seat2 = new SeatDto(1, 1, 2);
        seatArr.add(seat1);
        seatArr.add(seat2);
        ReservationDto reservationDto = new ReservationDto(seatArr);

        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(redisTemplate.opsForValue()
                           .get(any())).willReturn("2");

        //when
        CustomException customException = assertThrows(CustomException.class,
            () -> reservationService.reserveSeats(reservationDto, 1));

        //then
        assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.NOT_MY_SEAT);
    }
}