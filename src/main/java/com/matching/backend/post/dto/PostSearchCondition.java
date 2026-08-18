package com.matching.backend.post.dto;

import java.time.LocalDateTime;

import com.matching.backend.post.entity.BoardType;
import com.matching.backend.post.entity.PostStatus;
import com.matching.backend.post.entity.RoleType;

public record PostSearchCondition(
        BoardType boardType,
        RoleType roleType,
        PostStatus status,
        String region,
        LocalDateTime matchDateFrom,
        LocalDateTime matchDateTo
) {
}
