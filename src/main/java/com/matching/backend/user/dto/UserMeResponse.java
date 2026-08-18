package com.matching.backend.user.dto;

import java.time.LocalDateTime;

import com.matching.backend.user.entity.Gender;
import com.matching.backend.user.entity.Position;
import com.matching.backend.user.entity.User;

public record UserMeResponse(
        Long id,
        String email,
        String nickname,
        Integer age,
        Gender gender,
        String region,
        Position position,
        String skillLevel,
        String career,
        String videoUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static UserMeResponse from(User user) {
        return new UserMeResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getAge(),
                user.getGender(),
                user.getRegion(),
                user.getPosition(),
                user.getSkillLevel(),
                user.getCareer(),
                user.getVideoUrl(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
