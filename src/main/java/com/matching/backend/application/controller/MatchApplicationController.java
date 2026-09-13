package com.matching.backend.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.matching.backend.application.dto.ApplicationCreateRequest;
import com.matching.backend.application.dto.ApplicationResponse;
import com.matching.backend.application.service.MatchApplicationService;
import com.matching.backend.auth.security.AuthUserPrincipal;
import com.matching.backend.auth.security.CurrentUser;
import com.matching.backend.common.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class MatchApplicationController {

    private final MatchApplicationService applicationService;

    public MatchApplicationController(MatchApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/posts/{postId}/applications")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ApplicationResponse> apply(
            @CurrentUser AuthUserPrincipal principal,
            @PathVariable Long postId,
            @Valid @RequestBody ApplicationCreateRequest request
    ) {
        return ApiResponse.success(applicationService.apply(principal.userId(), postId, request));
    }

    @GetMapping("/posts/{postId}/applications")
    public ApiResponse<List<ApplicationResponse>> getPostApplications(
            @CurrentUser AuthUserPrincipal principal,
            @PathVariable Long postId
    ) {
        return ApiResponse.success(applicationService.getPostApplications(principal.userId(), postId));
    }

    @GetMapping("/applications/me")
    public ApiResponse<List<ApplicationResponse>> getMyApplications(@CurrentUser AuthUserPrincipal principal) {
        return ApiResponse.success(applicationService.getMyApplications(principal.userId()));
    }

    @PatchMapping("/applications/{applicationId}/accept")
    public ApiResponse<ApplicationResponse> accept(
            @CurrentUser AuthUserPrincipal principal,
            @PathVariable Long applicationId
    ) {
        return ApiResponse.success(applicationService.accept(principal.userId(), applicationId));
    }

    @PatchMapping("/applications/{applicationId}/reject")
    public ApiResponse<ApplicationResponse> reject(
            @CurrentUser AuthUserPrincipal principal,
            @PathVariable Long applicationId
    ) {
        return ApiResponse.success(applicationService.reject(principal.userId(), applicationId));
    }
}
