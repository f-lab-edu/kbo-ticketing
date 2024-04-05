package com.kboticketing.kboticketing.dao;

import com.kboticketing.kboticketing.domain.Reservation;
import com.kboticketing.kboticketing.domain.SeatGrade;
import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hazel
 */
@Mapper
public interface SeatMapper {

    ArrayList<Reservation> selectReservations(String scheduleId, String seatGradeId);

    SeatGrade selectSeatGrade(String id);
}
