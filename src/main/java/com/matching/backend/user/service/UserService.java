package com.matching.backend.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matching.backend.common.exception.BusinessException;
import com.matching.backend.common.exception.ErrorCode;
import com.matching.backend.user.dto.UserMeResponse;
import com.matching.backend.user.entity.User;
import com.matching.backend.user.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserMeResponse getMe(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return UserMeResponse.from(user);
    }
}
