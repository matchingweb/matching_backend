package com.matching.backend.post.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.matching.backend.post.dto.PostSearchCondition;
import com.matching.backend.post.entity.Post;

public class PostSpecification {

    private PostSpecification() {
    }

    public static Specification<Post> search(PostSearchCondition condition) {
        return Specification
                .where(boardTypeEquals(condition))
                .and(roleTypeEquals(condition))
                .and(statusEquals(condition))
                .and(regionContains(condition))
                .and(matchDateGreaterThanOrEqualTo(condition))
                .and(matchDateLessThanOrEqualTo(condition));
    }

    private static Specification<Post> boardTypeEquals(PostSearchCondition condition) {
        return (root, query, criteriaBuilder) -> condition.boardType() == null
                ? null
                : criteriaBuilder.equal(root.get("boardType"), condition.boardType());
    }

    private static Specification<Post> roleTypeEquals(PostSearchCondition condition) {
        return (root, query, criteriaBuilder) -> condition.roleType() == null
                ? null
                : criteriaBuilder.equal(root.get("roleType"), condition.roleType());
    }

    private static Specification<Post> statusEquals(PostSearchCondition condition) {
        return (root, query, criteriaBuilder) -> condition.status() == null
                ? null
                : criteriaBuilder.equal(root.get("status"), condition.status());
    }

    private static Specification<Post> regionContains(PostSearchCondition condition) {
        return (root, query, criteriaBuilder) -> !StringUtils.hasText(condition.region())
                ? null
                : criteriaBuilder.like(root.get("location"), "%" + condition.region() + "%");
    }

    private static Specification<Post> matchDateGreaterThanOrEqualTo(PostSearchCondition condition) {
        return (root, query, criteriaBuilder) -> condition.matchDateFrom() == null
                ? null
                : criteriaBuilder.greaterThanOrEqualTo(root.get("matchDate"), condition.matchDateFrom());
    }

    private static Specification<Post> matchDateLessThanOrEqualTo(PostSearchCondition condition) {
        return (root, query, criteriaBuilder) -> condition.matchDateTo() == null
                ? null
                : criteriaBuilder.lessThanOrEqualTo(root.get("matchDate"), condition.matchDateTo());
    }
}
