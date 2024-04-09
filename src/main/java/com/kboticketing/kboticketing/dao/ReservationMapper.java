package com.kboticketing.kboticketing.dao;

import com.kboticketing.kboticketing.dto.SeatDto;
import java.time.LocalDateTime;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hazel
 */
@Mapper
public interface ReservationMapper {

    Integer insert(SeatDto seatDto, Integer userId, LocalDateTime currentTime) throws Exception;

    Boolean checkReservationLimit(Integer ScheduleId);
}
