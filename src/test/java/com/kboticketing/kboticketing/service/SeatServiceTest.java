package com.kboticketing.kboticketing.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import com.kboticketing.kboticketing.dao.SeatMapper;
import com.kboticketing.kboticketing.domain.Reservation;
import com.kboticketing.kboticketing.domain.SeatGrade;
import com.kboticketing.kboticketing.dto.ReservationSeatDto;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author hazel
 */
@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @Mock
    private SeatMapper seatMapper;

    @InjectMocks
    private SeatService seatService;

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
}