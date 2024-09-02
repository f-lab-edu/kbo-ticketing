package com.kboticketing.kboticketing.dao;

import com.kboticketing.kboticketing.domain.Team;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hazel
 */
@Mapper
public interface TeamMapper {

    List<Team> selectTeams();

}
