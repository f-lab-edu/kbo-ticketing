package com.kboticketing.kboticketing.dao;

import com.kboticketing.kboticketing.dto.ReservationDto;
import java.time.LocalDateTime;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hazel
 */
@Mapper
public interface ReservationMapper {

    Integer insert(ReservationDto reservationDto, Integer userId, LocalDateTime currentTime)
        throws Exception;

    Boolean checkReservationLimit(Integer ScheduleId);
}
