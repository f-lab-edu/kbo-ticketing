package com.kboticketing.kboticketing.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kboticketing.kboticketing.domain.SeatGrade;
import com.kboticketing.kboticketing.dto.ReservationSeatDto;
import com.kboticketing.kboticketing.service.SeatService;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author hazel
 */
@WebMvcTest(SeatController.class)
@ExtendWith(MockitoExtension.class)
class SeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SeatService seatService;

    @Test
    @DisplayName("[SUCCESS]예약 좌석 목록 조회 테스트")
    public void getSeatsByGradeTest() throws Exception {

        //given
        ReservationSeatDto reservationSeatDto = new ReservationSeatDto(new ArrayList<>());
        reservationSeatDto.getSeatNumbers()
                          .add(1);
        reservationSeatDto.getSeatNumbers()
                          .add(2);
        given(seatService.getSeatsByGrade(any(), any()))
            .willReturn(reservationSeatDto);

        //when, then
        mockMvc.perform(get("/schedules/1/seat-grades/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data.seatNumbers[0]").value(1))
               .andExpect(jsonPath("$.data.seatNumbers[1]").value(2));
    }

    @Test
    @DisplayName("[SUCCESS] 좌석 정보 조회 테스트")
    public void getSeatGradeTest() throws Exception {

        //given
        SeatGrade seatGrade = new SeatGrade(1, "블루석 300블럭", "300");
        given(seatService.getSeatGrade(any())).willReturn(seatGrade);

        //when, then
        mockMvc.perform(get("/seat-grades/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data.seatGradeId").value(1))
               .andExpect(jsonPath("$.data.seatGradeName").value("블루석 300블럭"))
               .andExpect(jsonPath("$.data.seatCount").value("300"));
    }
}