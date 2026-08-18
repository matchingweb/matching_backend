package com.matching.backend.post.dto;

import java.time.LocalDateTime;

import com.matching.backend.post.entity.BoardType;
import com.matching.backend.post.entity.RoleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostCreateRequest(
        Long teamId,

        @NotNull(message = "게시판 유형은 필수입니다.")
        BoardType boardType,

        @NotNull(message = "역할 유형은 필수입니다.")
        RoleType roleType,

        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 100, message = "제목은 100자 이하여야 합니다.")
        String title,

        LocalDateTime matchDate,

        @Size(max = 200, message = "장소는 200자 이하여야 합니다.")
        String location,

        @NotBlank(message = "내용은 필수입니다.")
        String content
) {
}
