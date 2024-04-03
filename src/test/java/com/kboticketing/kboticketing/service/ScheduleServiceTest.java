package com.kboticketing.kboticketing.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

import com.kboticketing.kboticketing.dao.ScheduleMapper;
import com.kboticketing.kboticketing.domain.ScheduleInfo;
import com.kboticketing.kboticketing.domain.ScheduleTeam;
import com.kboticketing.kboticketing.domain.SeatGradeBySchedule;
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

    @Test
    @DisplayName("[SUCCESS] 경기 정보 조회 테스트")
    public void getScheduleTest() {

        //given
        ScheduleInfo scheduleInfo = new ScheduleInfo(1, "LG vs KT", "2024-03-29 18:30:00",
            "서울종합운동장 야구장");
        given(scheduleMapper.selectInfo(any())).willReturn(scheduleInfo);

        //when, then
        assertThat(scheduleService.getSchedule(any())).isEqualTo(scheduleInfo);
    }

    @Test
    @DisplayName("[SUCCESS] 좌석 목록 조회 테스트")
    public void getSeatGradeTest() {

        //given
        ArrayList<SeatGradeBySchedule> seatGradeList = new ArrayList<>();
        seatGradeList.add(new SeatGradeBySchedule(1, 1, "블루석", "20000", "500"));
        given(scheduleMapper.selectSeatGrade(any())).willReturn(seatGradeList);

        //when, then
        assertThat(scheduleService.getSeatGradeBySchedule(any())).isEqualTo(seatGradeList);
    }
}