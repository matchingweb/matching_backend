package com.matching.backend.post.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.matching.backend.common.exception.BusinessException;
import com.matching.backend.common.exception.ErrorCode;
import com.matching.backend.post.dto.PostCreateRequest;
import com.matching.backend.post.dto.PostResponse;
import com.matching.backend.post.dto.PostSearchCondition;
import com.matching.backend.post.dto.PostUpdateRequest;
import com.matching.backend.post.entity.BoardType;
import com.matching.backend.post.entity.Post;
import com.matching.backend.post.repository.PostRepository;
import com.matching.backend.post.repository.PostSpecification;
import com.matching.backend.team.entity.Team;
import com.matching.backend.team.repository.TeamRepository;
import com.matching.backend.user.entity.User;
import com.matching.backend.user.repository.UserRepository;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public PostService(
            PostRepository postRepository,
            UserRepository userRepository,
            TeamRepository teamRepository
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    public PostResponse createPost(Long authorUserId, PostCreateRequest request) {
        validatePostFields(request.boardType(), request.matchDate(), request.location());

        User author = userRepository.findById(authorUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Team team = findTeamForWriting(authorUserId, request.teamId());

        Post post = Post.create(
                author,
                team,
                request.boardType(),
                request.roleType(),
                request.title(),
                request.matchDate(),
                request.location(),
                request.content()
        );

        return PostResponse.from(postRepository.save(post));
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPosts(PostSearchCondition condition) {
        return postRepository.findAll(
                        PostSpecification.search(condition),
                        Sort.by(Sort.Direction.DESC, "createdAt")
                )
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) {
        return PostResponse.from(findPost(postId));
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getMyPosts(Long authorUserId) {
        return postRepository.findByAuthor_IdOrderByCreatedAtDesc(authorUserId)
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    @Transactional
    public PostResponse updatePost(Long userId, Long postId, PostUpdateRequest request) {
        validateTextWhenPresent(request.title());
        validateTextWhenPresent(request.content());

        Post post = findPost(postId);
        validatePostAuthor(userId, post);

        BoardType boardType = request.boardType() == null ? post.getBoardType() : request.boardType();
        LocalDateTime matchDate = request.matchDate() == null ? post.getMatchDate() : request.matchDate();
        String location = request.location() == null ? post.getLocation() : request.location();
        validatePostFields(boardType, matchDate, location);

        Team team = request.teamId() == null ? null : findTeamForWriting(userId, request.teamId());
        post.update(
                team,
                request.boardType(),
                request.roleType(),
                request.title(),
                request.matchDate(),
                request.location(),
                request.content()
        );

        return PostResponse.from(post);
    }

    @Transactional
    public PostResponse closePost(Long userId, Long postId) {
        Post post = findPost(postId);
        validatePostAuthor(userId, post);
        post.close();

        return PostResponse.from(post);
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
    }

    private Team findTeamForWriting(Long userId, Long teamId) {
        if (teamId == null) {
            return null;
        }

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new BusinessException(ErrorCode.TEAM_NOT_FOUND));
        if (!team.isOwnedBy(userId)) {
            throw new BusinessException(ErrorCode.POST_TEAM_FORBIDDEN);
        }

        return team;
    }

    private void validatePostAuthor(Long userId, Post post) {
        if (!post.isWrittenBy(userId)) {
            throw new BusinessException(ErrorCode.POST_FORBIDDEN);
        }
    }

    private void validatePostFields(BoardType boardType, LocalDateTime matchDate, String location) {
        if ((boardType == BoardType.MERCENARY || boardType == BoardType.TEAM_MATCH)
                && (matchDate == null || !StringUtils.hasText(location))) {
            throw new BusinessException(
                    ErrorCode.INVALID_REQUEST,
                    "용병 매칭과 팀 교류전 게시글은 경기 일시와 장소가 필수입니다."
            );
        }
    }

    private void validateTextWhenPresent(String value) {
        if (value != null && !StringUtils.hasText(value)) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST);
        }
    }
}
