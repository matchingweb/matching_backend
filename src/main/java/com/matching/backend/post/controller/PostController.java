package com.matching.backend.post.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.matching.backend.auth.security.AuthUserPrincipal;
import com.matching.backend.common.response.ApiResponse;
import com.matching.backend.post.dto.PostCreateRequest;
import com.matching.backend.post.dto.PostResponse;
import com.matching.backend.post.dto.PostUpdateRequest;
import com.matching.backend.post.service.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PostResponse> createPost(
            @AuthenticationPrincipal AuthUserPrincipal principal,
            @Valid @RequestBody PostCreateRequest request
    ) {
        return ApiResponse.success(postService.createPost(principal.userId(), request));
    }

    @GetMapping
    public ApiResponse<List<PostResponse>> getPosts() {
        return ApiResponse.success(postService.getPosts());
    }

    @GetMapping("/me")
    public ApiResponse<List<PostResponse>> getMyPosts(@AuthenticationPrincipal AuthUserPrincipal principal) {
        return ApiResponse.success(postService.getMyPosts(principal.userId()));
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long postId) {
        return ApiResponse.success(postService.getPost(postId));
    }

    @PatchMapping("/{postId}")
    public ApiResponse<PostResponse> updatePost(
            @AuthenticationPrincipal AuthUserPrincipal principal,
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequest request
    ) {
        return ApiResponse.success(postService.updatePost(principal.userId(), postId, request));
    }

    @PatchMapping("/{postId}/close")
    public ApiResponse<PostResponse> closePost(
            @AuthenticationPrincipal AuthUserPrincipal principal,
            @PathVariable Long postId
    ) {
        return ApiResponse.success(postService.closePost(principal.userId(), postId));
    }
}
