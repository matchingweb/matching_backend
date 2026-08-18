package com.matching.backend.auth.dto;

import com.matching.backend.user.entity.Gender;
import com.matching.backend.user.entity.Position;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        @Size(max = 100, message = "이메일은 100자 이하여야 합니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, max = 72, message = "비밀번호는 8자 이상 72자 이하여야 합니다.")
        String password,

        @NotBlank(message = "닉네임은 필수입니다.")
        @Size(max = 30, message = "닉네임은 30자 이하여야 합니다.")
        String nickname,

        @NotNull(message = "나이는 필수입니다.")
        @Min(value = 1, message = "나이는 1세 이상이어야 합니다.")
        @Max(value = 100, message = "나이는 100세 이하여야 합니다.")
        Integer age,

        @NotNull(message = "성별은 필수입니다.")
        Gender gender,

        @NotBlank(message = "주 활동 지역은 필수입니다.")
        @Size(max = 100, message = "주 활동 지역은 100자 이하여야 합니다.")
        String region,

        @NotNull(message = "포지션은 필수입니다.")
        Position position,

        @Size(max = 100, message = "실력 정보는 100자 이하여야 합니다.")
        String skillLevel,

        @Size(max = 500, message = "경력 정보는 500자 이하여야 합니다.")
        String career,

        @Size(max = 500, message = "경기 영상 URL은 500자 이하여야 합니다.")
        String videoUrl
) {
}
