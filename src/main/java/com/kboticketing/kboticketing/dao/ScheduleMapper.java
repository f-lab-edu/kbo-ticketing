package com.kboticketing.kboticketing.dao;

import com.kboticketing.kboticketing.domain.ScheduleInfo;
import com.kboticketing.kboticketing.domain.ScheduleTeam;
import com.kboticketing.kboticketing.dto.ScheduleQueryParamDto;
import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hazel
 */
@Mapper
public interface ScheduleMapper {

    ArrayList<ScheduleTeam> selectSchedules(ScheduleQueryParamDto scheduleQueryParamDto);

    ScheduleInfo selectInfo(String id);
}
