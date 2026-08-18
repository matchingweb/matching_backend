package com.matching.backend.team.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.matching.backend.auth.security.AuthUserPrincipal;
import com.matching.backend.common.response.ApiResponse;
import com.matching.backend.team.dto.TeamCreateRequest;
import com.matching.backend.team.dto.TeamResponse;
import com.matching.backend.team.dto.TeamUpdateRequest;
import com.matching.backend.team.service.TeamService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TeamResponse> createTeam(
            @AuthenticationPrincipal AuthUserPrincipal principal,
            @Valid @RequestBody TeamCreateRequest request
    ) {
        return ApiResponse.success(teamService.createTeam(principal.userId(), request));
    }

    @GetMapping("/{teamId}")
    public ApiResponse<TeamResponse> getTeam(@PathVariable Long teamId) {
        return ApiResponse.success(teamService.getTeam(teamId));
    }

    @PatchMapping("/{teamId}")
    public ApiResponse<TeamResponse> updateTeam(
            @AuthenticationPrincipal AuthUserPrincipal principal,
            @PathVariable Long teamId,
            @Valid @RequestBody TeamUpdateRequest request
    ) {
        return ApiResponse.success(teamService.updateTeam(principal.userId(), teamId, request));
    }
}
