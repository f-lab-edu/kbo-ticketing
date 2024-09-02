package com.kboticketing.kboticketing.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

import com.kboticketing.kboticketing.dao.TeamMapper;
import com.kboticketing.kboticketing.domain.Team;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author hazel
 */

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamMapper teamMapper;

    @InjectMocks
    private TeamService teamService;

    @Test
    @DisplayName("[SUCCESS] 팀 리스트 테스트")
    public void getTeamsTest() {

        //given
        List<Team> fakeTeams = Arrays.asList(
            new Team(1, "Team 1"),
            new Team(2, "Team 2"),
            new Team(3, "Team 3")
        );
        given(teamMapper.selectTeams()).willReturn(fakeTeams);

        //when, then
        assertThat(teamService.getTeams()).isEqualTo(fakeTeams);
    }
}