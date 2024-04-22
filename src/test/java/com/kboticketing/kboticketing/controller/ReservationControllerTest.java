package com.kboticketing.kboticketing.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kboticketing.kboticketing.dto.ReservationDto;
import com.kboticketing.kboticketing.dto.SeatDto;
import com.kboticketing.kboticketing.service.ReservationService;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author hazel
 */
@WebMvcTest(ReservationController.class)
@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReservationService reservationService;

    @Test
    @DisplayName("[SUCCESS]예매 성공 테스트")
    public void reserveSeatRequestTest() throws Exception {

        //given
        ArrayList<SeatDto> seatArr = new ArrayList<>();
        SeatDto seat1 = new SeatDto(1, 1, 1);
        SeatDto seat2 = new SeatDto(1, 1, 2);
        seatArr.add(seat1);
        seatArr.add(seat2);
        ReservationDto reservationDto = new ReservationDto(seatArr);

        willDoNothing().given(reservationService)
                       .reserveSeats(any(ReservationDto.class), any(Integer.class));

        String json = new ObjectMapper().writeValueAsString(reservationDto);

        //when,then
        mockMvc.perform(post("/reservations")
                   .content(json)
                   .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }
}