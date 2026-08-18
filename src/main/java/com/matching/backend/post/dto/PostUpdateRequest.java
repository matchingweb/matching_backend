package com.matching.backend.post.dto;

import java.time.LocalDateTime;

import com.matching.backend.post.entity.BoardType;
import com.matching.backend.post.entity.RoleType;

import jakarta.validation.constraints.Size;

public record PostUpdateRequest(
        Long teamId,
        BoardType boardType,
        RoleType roleType,

        @Size(max = 100, message = "제목은 100자 이하여야 합니다.")
        String title,

        LocalDateTime matchDate,

        @Size(max = 200, message = "장소는 200자 이하여야 합니다.")
        String location,

        String content
) {
}
