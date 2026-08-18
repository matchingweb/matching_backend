package com.matching.backend.post.dto;

import java.time.LocalDateTime;

import com.matching.backend.post.entity.BoardType;
import com.matching.backend.post.entity.Post;
import com.matching.backend.post.entity.PostStatus;
import com.matching.backend.post.entity.RoleType;

public record PostResponse(
        Long id,
        Long authorUserId,
        String authorNickname,
        Long teamId,
        String teamName,
        BoardType boardType,
        RoleType roleType,
        PostStatus status,
        String title,
        LocalDateTime matchDate,
        String location,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getAuthor().getId(),
                post.getAuthor().getNickname(),
                post.getTeam() == null ? null : post.getTeam().getId(),
                post.getTeam() == null ? null : post.getTeam().getName(),
                post.getBoardType(),
                post.getRoleType(),
                post.getStatus(),
                post.getTitle(),
                post.getMatchDate(),
                post.getLocation(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
