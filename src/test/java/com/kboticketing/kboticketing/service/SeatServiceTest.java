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
import com.kboticketing.kboticketing.exception.CustomException;
import com.kboticketing.kboticketing.exception.ErrorCode;
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
    @DisplayName("[SUCCESS]좌석 선택 테스트")
    public void selectSeatTest() {

        //given
        SeatDto seatDto = new SeatDto(1, 1, 1);
        given(redisTemplate.execute(any(), anyList(), any())).willReturn("SUCCESS");

        //when, then : 아무런 예외를 던지지 않음.
        assertDoesNotThrow(() -> seatService.selectSeats(seatDto, 1));
    }

    @Test
    @DisplayName("Redis Lua Script 를 이용한 좌석 선택 동시성 테스트")
    public void Test() {
        // ExecutorService 사용해 스레드 풀 생성
        ExecutorService executor = Executors.newFixedThreadPool(100);

        // 100만 번의 호출을 위한 랜덤 객체 생성
        Random random = new Random();

        // 각 호출의 처리 시작 시간을 저장할 리스트
        List<Long> startTimes = new ArrayList<>();

        // 100만 번의 호출을 위한 루프
        for (int i = 0; i < 1000000; i++) {
            // 랜덤한 SeatDto , userId 생성
            SeatDto seatDto = new SeatDto(random.nextInt(100), random.nextInt(100),
                random.nextInt(100));
            Integer userId = random.nextInt(1000);

            // 호출 직전의 시간 기록
            long startTime = System.nanoTime();
            startTimes.add(startTime);

            // 스레드 풀에 작업 추가
            executor.submit(() -> {
                seatService.selectSeats(seatDto, userId);
            });
        }

        // 전체 소요 시간 계산
        long firstStartTime = startTimes.get(0); //처음시간
        long lastStartTime = startTimes.get(startTimes.size() - 1); //마지막 시간
        long totalElapsedTime = lastStartTime - firstStartTime;

        // 전체 소요 시간 출력
        double totalElapsedTimeSeconds = totalElapsedTime / 1_000_000_000.0;
        System.out.println("처음 호출부터 1000000번 호출까지 소요 시간: " + totalElapsedTimeSeconds + " s");
        System.out.println("모든 작업이 완료되었습니다.");
    }
}