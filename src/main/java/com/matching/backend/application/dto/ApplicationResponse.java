package com.matching.backend.application.dto;

import java.time.LocalDateTime;

import com.matching.backend.application.entity.ApplicationStatus;
import com.matching.backend.application.entity.MatchApplication;

public record ApplicationResponse(
        Long id,
        Long postId,
        String postTitle,
        Long applicantUserId,
        String applicantNickname,
        ApplicationStatus status,
        String message,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static ApplicationResponse from(MatchApplication application) {
        return new ApplicationResponse(
                application.getId(),
                application.getPost().getId(),
                application.getPost().getTitle(),
                application.getApplicant().getId(),
                application.getApplicant().getNickname(),
                application.getStatus(),
                application.getMessage(),
                application.getCreatedAt(),
                application.getUpdatedAt()
        );
    }
}
