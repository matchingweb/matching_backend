package com.matching.backend.post.controller;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.matching.backend.auth.security.AuthUserPrincipal;
import com.matching.backend.auth.security.CurrentUser;
import com.matching.backend.common.response.ApiResponse;
import com.matching.backend.common.response.PageResponse;
import com.matching.backend.post.dto.PostCreateRequest;
import com.matching.backend.post.dto.PostResponse;
import com.matching.backend.post.dto.PostSearchCondition;
import com.matching.backend.post.dto.PostUpdateRequest;
import com.matching.backend.post.entity.BoardType;
import com.matching.backend.post.entity.PostStatus;
import com.matching.backend.post.entity.RoleType;
import com.matching.backend.post.service.PostService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/posts")
@Validated
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PostResponse> createPost(
            @CurrentUser AuthUserPrincipal principal,
            @Valid @RequestBody PostCreateRequest request
    ) {
        return ApiResponse.success(postService.createPost(principal.userId(), request));
    }

    @GetMapping
    public ApiResponse<PageResponse<PostResponse>> getPosts(
            @RequestParam(required = false) BoardType boardType,
            @RequestParam(required = false) RoleType roleType,
            @RequestParam(required = false) PostStatus status,
            @RequestParam(required = false) String region,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime matchDateFrom,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime matchDateTo,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size
    ) {
        PostSearchCondition condition = new PostSearchCondition(
                boardType,
                roleType,
                status,
                region,
                matchDateFrom,
                matchDateTo
        );
        return ApiResponse.success(postService.getPosts(condition, page, size));
    }

    @GetMapping("/me")
    public ApiResponse<PageResponse<PostResponse>> getMyPosts(
            @CurrentUser AuthUserPrincipal principal,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size
    ) {
        return ApiResponse.success(postService.getMyPosts(principal.userId(), page, size));
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long postId) {
        return ApiResponse.success(postService.getPost(postId));
    }

    @PatchMapping("/{postId}")
    public ApiResponse<PostResponse> updatePost(
            @CurrentUser AuthUserPrincipal principal,
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequest request
    ) {
        return ApiResponse.success(postService.updatePost(principal.userId(), postId, request));
    }

    @PatchMapping("/{postId}/close")
    public ApiResponse<PostResponse> closePost(
            @CurrentUser AuthUserPrincipal principal,
            @PathVariable Long postId
    ) {
        return ApiResponse.success(postService.closePost(principal.userId(), postId));
    }
}
