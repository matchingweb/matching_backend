package com.matching.backend.application.entity;

import com.matching.backend.common.entity.BaseTimeEntity;
import com.matching.backend.post.entity.Post;
import com.matching.backend.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "post_applications",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_post_applications_post_applicant",
                columnNames = {"post_id", "applicant_user_id"}
        )
)
public class MatchApplication extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "applicant_user_id", nullable = false)
    private User applicant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ApplicationStatus status;

    @Column(length = 500)
    private String message;

    protected MatchApplication() {
    }

    private MatchApplication(Post post, User applicant, String message) {
        this.post = post;
        this.applicant = applicant;
        this.status = ApplicationStatus.PENDING;
        this.message = message;
    }

    public static MatchApplication create(Post post, User applicant, String message) {
        return new MatchApplication(post, applicant, message);
    }

    public void accept() {
        this.status = ApplicationStatus.ACCEPTED;
    }

    public void reject() {
        this.status = ApplicationStatus.REJECTED;
    }

    public boolean isPostWrittenBy(Long userId) {
        return post.isWrittenBy(userId);
    }

    public Long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public User getApplicant() {
        return applicant;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
