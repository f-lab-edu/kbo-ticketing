package com.kboticketing.kboticketing.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kboticketing.kboticketing.domain.ScheduleTeam;
import com.kboticketing.kboticketing.service.ScheduleService;
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
@WebMvcTest(ScheduleController.class)
@ExtendWith(MockitoExtension.class)
class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ScheduleService scheduleService;

    @Test
    @DisplayName("[SUCCESS] 일정 목록 조회 테스트")
    public void getSchedulesTest() throws Exception {

        //given
        ArrayList<ScheduleTeam> scheduleTeams = new ArrayList<>();
        scheduleTeams.add(new ScheduleTeam(1, "LG vs SSG", "잠실종합운동장", "2024-04-01 18:30:00"));
        given(scheduleService.getSchedules(any())).willReturn(scheduleTeams);

        //when, then
        mockMvc.perform(get("/schedules"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data[0].scheduleId").value(scheduleTeams.get(0)
                                                                              .getScheduleId()));
    }
}