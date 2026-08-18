package com.matching.backend.auth.dto;

import com.matching.backend.user.entity.Gender;
import com.matching.backend.user.entity.Position;
import com.matching.backend.user.entity.User;

public record SignupResponse(
        Long id,
        String email,
        String nickname,
        Integer age,
        Gender gender,
        String region,
        Position position
) {

    public static SignupResponse from(User user) {
        return new SignupResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getAge(),
                user.getGender(),
                user.getRegion(),
                user.getPosition()
        );
    }
}
