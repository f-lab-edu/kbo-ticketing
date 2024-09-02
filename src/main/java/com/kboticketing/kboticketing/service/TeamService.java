package com.kboticketing.kboticketing.service;

import com.kboticketing.kboticketing.domain.Team;
import com.kboticketing.kboticketing.repository.TeamRepository;
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

    private final TeamRepository teamRepository;

    @Cacheable(value = "teams/")
    public List<Team> getTeams() {
        return teamRepository.findAll();
    }
}
