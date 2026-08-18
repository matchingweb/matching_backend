package com.matching.backend.team.dto;

import com.matching.backend.team.entity.TeamLevel;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TeamCreateRequest(
        @NotBlank(message = "팀명은 필수입니다.")
        @Size(max = 100, message = "팀명은 100자 이하여야 합니다.")
        String name,

        @Size(max = 500, message = "로고 URL은 500자 이하여야 합니다.")
        String logoUrl,

        @NotBlank(message = "연고지는 필수입니다.")
        @Size(max = 100, message = "연고지는 100자 이하여야 합니다.")
        String homeRegion,

        @Size(max = 100, message = "홈구장은 100자 이하여야 합니다.")
        String homeStadium,

        @NotBlank(message = "팀 연령대는 필수입니다.")
        @Size(max = 50, message = "팀 연령대는 50자 이하여야 합니다.")
        String ageGroup,

        @NotNull(message = "팀 수준은 필수입니다.")
        TeamLevel level,

        @Min(value = 0, message = "회비는 0원 이상이어야 합니다.")
        Integer fee
) {
}
