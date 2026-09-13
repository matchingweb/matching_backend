package com.matching.backend.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matching.backend.application.dto.ApplicationCreateRequest;
import com.matching.backend.application.dto.ApplicationResponse;
import com.matching.backend.application.entity.MatchApplication;
import com.matching.backend.application.repository.MatchApplicationRepository;
import com.matching.backend.common.exception.BusinessException;
import com.matching.backend.common.exception.ErrorCode;
import com.matching.backend.post.entity.Post;
import com.matching.backend.post.entity.PostStatus;
import com.matching.backend.post.repository.PostRepository;
import com.matching.backend.user.entity.User;
import com.matching.backend.user.repository.UserRepository;

@Service
public class MatchApplicationService {

    private final MatchApplicationRepository applicationRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public MatchApplicationService(
            MatchApplicationRepository applicationRepository,
            PostRepository postRepository,
            UserRepository userRepository
    ) {
        this.applicationRepository = applicationRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ApplicationResponse apply(Long applicantUserId, Long postId, ApplicationCreateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        if (post.getStatus() == PostStatus.CLOSED) {
            throw new BusinessException(ErrorCode.POST_CLOSED);
        }
        if (post.isWrittenBy(applicantUserId)) {
            throw new BusinessException(ErrorCode.SELF_APPLICATION_NOT_ALLOWED);
        }
        if (applicationRepository.existsByPost_IdAndApplicant_Id(postId, applicantUserId)) {
            throw new BusinessException(ErrorCode.DUPLICATE_APPLICATION);
        }

        User applicant = userRepository.findById(applicantUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        MatchApplication application = MatchApplication.create(post, applicant, request.message());
        return ApplicationResponse.from(applicationRepository.save(application));
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> getPostApplications(Long userId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
        if (!post.isWrittenBy(userId)) {
            throw new BusinessException(ErrorCode.APPLICATION_FORBIDDEN);
        }

        return applicationRepository.findByPost_IdOrderByCreatedAtDesc(postId)
                .stream()
                .map(ApplicationResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> getMyApplications(Long applicantUserId) {
        return applicationRepository.findByApplicant_IdOrderByCreatedAtDesc(applicantUserId)
                .stream()
                .map(ApplicationResponse::from)
                .toList();
    }

    @Transactional
    public ApplicationResponse accept(Long userId, Long applicationId) {
        MatchApplication application = findApplication(applicationId);
        validatePostAuthor(userId, application);
        application.accept();
        return ApplicationResponse.from(application);
    }

    @Transactional
    public ApplicationResponse reject(Long userId, Long applicationId) {
        MatchApplication application = findApplication(applicationId);
        validatePostAuthor(userId, application);
        application.reject();
        return ApplicationResponse.from(application);
    }

    private MatchApplication findApplication(Long applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_FOUND));
    }

    private void validatePostAuthor(Long userId, MatchApplication application) {
        if (!application.isPostWrittenBy(userId)) {
            throw new BusinessException(ErrorCode.APPLICATION_FORBIDDEN);
        }
    }
}
