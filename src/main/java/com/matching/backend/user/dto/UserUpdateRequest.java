package com.matching.backend.user.dto;

import com.matching.backend.user.entity.Gender;
import com.matching.backend.user.entity.Position;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Size(max = 30, message = "닉네임은 30자 이하여야 합니다.")
        String nickname,

        @Min(value = 1, message = "나이는 1세 이상이어야 합니다.")
        @Max(value = 100, message = "나이는 100세 이하여야 합니다.")
        Integer age,

        Gender gender,

        @Size(max = 100, message = "주 활동 지역은 100자 이하여야 합니다.")
        String region,

        Position position,

        @Size(max = 100, message = "실력 정보는 100자 이하여야 합니다.")
        String skillLevel,

        @Size(max = 500, message = "경력 정보는 500자 이하여야 합니다.")
        String career,

        @Size(max = 500, message = "경기 영상 URL은 500자 이하여야 합니다.")
        String videoUrl
) {
}
