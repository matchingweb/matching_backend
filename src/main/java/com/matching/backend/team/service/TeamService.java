package com.matching.backend.team.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.matching.backend.common.exception.BusinessException;
import com.matching.backend.common.exception.ErrorCode;
import com.matching.backend.team.dto.TeamCreateRequest;
import com.matching.backend.team.dto.TeamResponse;
import com.matching.backend.team.dto.TeamUpdateRequest;
import com.matching.backend.team.entity.Team;
import com.matching.backend.team.repository.TeamRepository;
import com.matching.backend.user.entity.User;
import com.matching.backend.user.repository.UserRepository;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TeamResponse createTeam(Long ownerUserId, TeamCreateRequest request) {
        User owner = userRepository.findById(ownerUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Team team = Team.create(
                owner,
                request.name(),
                request.logoUrl(),
                request.homeRegion(),
                request.homeStadium(),
                request.ageGroup(),
                request.level(),
                request.fee()
        );

        return TeamResponse.from(teamRepository.save(team));
    }

    @Transactional(readOnly = true)
    public TeamResponse getTeam(Long teamId) {
        return TeamResponse.from(findTeam(teamId));
    }

    @Transactional
    public TeamResponse updateTeam(Long userId, Long teamId, TeamUpdateRequest request) {
        validateRequiredTextWhenPresent(request.name());
        validateRequiredTextWhenPresent(request.homeRegion());
        validateRequiredTextWhenPresent(request.ageGroup());

        Team team = findTeam(teamId);
        if (!team.isOwnedBy(userId)) {
            throw new BusinessException(ErrorCode.TEAM_FORBIDDEN);
        }

        team.update(
                request.name(),
                request.logoUrl(),
                request.homeRegion(),
                request.homeStadium(),
                request.ageGroup(),
                request.level(),
                request.fee()
        );

        return TeamResponse.from(team);
    }

    private Team findTeam(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new BusinessException(ErrorCode.TEAM_NOT_FOUND));
    }

    private void validateRequiredTextWhenPresent(String value) {
        if (value != null && !StringUtils.hasText(value)) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST);
        }
    }
}
