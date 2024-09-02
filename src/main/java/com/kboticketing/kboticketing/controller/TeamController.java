package com.kboticketing.kboticketing.controller;

import com.kboticketing.kboticketing.domain.Team;
import com.kboticketing.kboticketing.service.TeamService;
import com.kboticketing.kboticketing.common.response.CommonResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hazel
 */
@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/teams")
    public ResponseEntity<CommonResponse> getTeams() {
        return ResponseEntity.ok(CommonResponse.ok(teamService.getTeams()));
    }
}
