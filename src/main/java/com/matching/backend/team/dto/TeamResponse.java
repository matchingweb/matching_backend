package com.matching.backend.team.dto;

import java.time.LocalDateTime;

import com.matching.backend.team.entity.Team;
import com.matching.backend.team.entity.TeamLevel;

public record TeamResponse(
        Long id,
        Long ownerUserId,
        String ownerNickname,
        String name,
        String logoUrl,
        String homeRegion,
        String homeStadium,
        String ageGroup,
        TeamLevel level,
        Integer fee,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static TeamResponse from(Team team) {
        return new TeamResponse(
                team.getId(),
                team.getOwner().getId(),
                team.getOwner().getNickname(),
                team.getName(),
                team.getLogoUrl(),
                team.getHomeRegion(),
                team.getHomeStadium(),
                team.getAgeGroup(),
                team.getLevel(),
                team.getFee(),
                team.getCreatedAt(),
                team.getUpdatedAt()
        );
    }
}
