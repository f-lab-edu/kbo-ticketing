package com.kboticketing.kboticketing.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

import com.kboticketing.kboticketing.dao.ScheduleMapper;
import com.kboticketing.kboticketing.domain.ScheduleTeam;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;

/**
 * @author hazel
 */
@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private ScheduleMapper scheduleMapper;

    @InjectMocks
    private ScheduleService scheduleService;

    @Test
    @DisplayName("[SUCCESS] 경기 목록 조회 테스트")
    public void getSchedulesTest() {

        //given
        ArrayList<ScheduleTeam> scheduleTeams = new ArrayList<>();
        scheduleTeams.add(new ScheduleTeam(1, "LG vs SSG", "잠실종합운동장", "2024-04-01 18:30:00"));
        given(scheduleMapper.selectSchedules(any())).willReturn(scheduleTeams);

        //when, then
        assertThat(scheduleService.getSchedules(any())).isEqualTo(scheduleTeams);
    }
}