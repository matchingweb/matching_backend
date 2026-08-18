package com.matching.backend.post.entity;

import java.time.LocalDateTime;

import com.matching.backend.common.entity.BaseTimeEntity;
import com.matching.backend.team.entity.Team;
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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_user_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private BoardType boardType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PostStatus status;

    @Column(nullable = false, length = 100)
    private String title;

    private LocalDateTime matchDate;

    @Column(length = 200)
    private String location;

    @Lob
    @Column(nullable = false)
    private String content;

    protected Post() {
    }

    private Post(
            User author,
            Team team,
            BoardType boardType,
            RoleType roleType,
            String title,
            LocalDateTime matchDate,
            String location,
            String content
    ) {
        this.author = author;
        this.team = team;
        this.boardType = boardType;
        this.roleType = roleType;
        this.status = PostStatus.OPEN;
        this.title = title;
        this.matchDate = matchDate;
        this.location = location;
        this.content = content;
    }

    public static Post create(
            User author,
            Team team,
            BoardType boardType,
            RoleType roleType,
            String title,
            LocalDateTime matchDate,
            String location,
            String content
    ) {
        return new Post(author, team, boardType, roleType, title, matchDate, location, content);
    }

    public void update(
            Team team,
            BoardType boardType,
            RoleType roleType,
            String title,
            LocalDateTime matchDate,
            String location,
            String content
    ) {
        if (team != null) {
            this.team = team;
        }
        if (boardType != null) {
            this.boardType = boardType;
        }
        if (roleType != null) {
            this.roleType = roleType;
        }
        if (title != null) {
            this.title = title;
        }
        if (matchDate != null) {
            this.matchDate = matchDate;
        }
        if (location != null) {
            this.location = location;
        }
        if (content != null) {
            this.content = content;
        }
    }

    public void close() {
        this.status = PostStatus.CLOSED;
    }

    public boolean isWrittenBy(Long userId) {
        return author.getId().equals(userId);
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Team getTeam() {
        return team;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public PostStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getMatchDate() {
        return matchDate;
    }

    public String getLocation() {
        return location;
    }

    public String getContent() {
        return content;
    }
}
