package com.kboticketing.kboticketing.service;

import com.kboticketing.kboticketing.dao.TeamMapper;
import com.kboticketing.kboticketing.domain.Team;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author hazel
 */
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamMapper teamMapper;

    @Cacheable(value = "teams/")
    public List<Team> getTeams() {
        return teamMapper.selectTeams();
    }
}
