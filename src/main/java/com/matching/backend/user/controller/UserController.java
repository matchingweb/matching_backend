package com.matching.backend.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matching.backend.auth.security.AuthUserPrincipal;
import com.matching.backend.auth.security.CurrentUser;
import com.matching.backend.common.response.ApiResponse;
import com.matching.backend.user.dto.UserMeResponse;
import com.matching.backend.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ApiResponse<UserMeResponse> getMe(@CurrentUser AuthUserPrincipal principal) {
        return ApiResponse.success(userService.getMe(principal.userId()));
    }
}
