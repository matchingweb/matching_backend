package com.matching.backend.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matching.backend.auth.dto.SignupRequest;
import com.matching.backend.auth.dto.SignupResponse;
import com.matching.backend.common.exception.BusinessException;
import com.matching.backend.common.exception.ErrorCode;
import com.matching.backend.user.entity.User;
import com.matching.backend.user.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        User user = User.create(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.nickname(),
                request.age(),
                request.gender(),
                request.region(),
                request.position(),
                request.skillLevel(),
                request.career(),
                request.videoUrl()
        );

        return SignupResponse.from(userRepository.save(user));
    }
}
