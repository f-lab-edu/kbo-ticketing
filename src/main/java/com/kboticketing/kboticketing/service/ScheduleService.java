package com.kboticketing.kboticketing.service;

import com.kboticketing.kboticketing.dao.ScheduleMapper;
import com.kboticketing.kboticketing.domain.ScheduleTeam;
import com.kboticketing.kboticketing.dto.ScheduleQueryParamDto;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author hazel
 */
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleMapper scheduleMapper;

    public ArrayList<ScheduleTeam> getSchedules(ScheduleQueryParamDto scheduleQueryParamDto) {
        return scheduleMapper.selectSchedules(scheduleQueryParamDto);
    }
}
