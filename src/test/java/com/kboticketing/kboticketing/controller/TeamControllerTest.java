package com.kboticketing.kboticketing.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kboticketing.kboticketing.domain.Team;
import com.kboticketing.kboticketing.service.TeamService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


/**
 * @author hazel
 */
@WebMvcTest(TeamController.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    @Test
    @DisplayName("[SUCCESS] 팀 리스트 테스트")
    public void getTeamsTest() throws Exception {

        //given
        List<Team> fakeTeams = Arrays.asList(
            new Team(1, "Team 1"),
            new Team(2, "Team 2"),
            new Team(3, "Team 3")
        );
        given(teamService.getTeams()).willReturn(fakeTeams);

        //when, then
        mockMvc.perform(get("/teams"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data[0].name").value("Team 1"))
               .andExpect(jsonPath("$.data[1].name").value("Team 2"))
               .andExpect(jsonPath("$.data[2].name").value("Team 3"));
    }
}