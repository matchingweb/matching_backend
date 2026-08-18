package com.matching.backend.user.entity;

import com.matching.backend.common.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Gender gender;

    @Column(nullable = false, length = 100)
    private String region;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Position position;

    @Column(length = 100)
    private String skillLevel;

    @Column(length = 500)
    private String career;

    @Column(length = 500)
    private String videoUrl;

    protected User() {
    }

    private User(
            String email,
            String password,
            String nickname,
            Integer age,
            Gender gender,
            String region,
            Position position,
            String skillLevel,
            String career,
            String videoUrl
    ) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.region = region;
        this.position = position;
        this.skillLevel = skillLevel;
        this.career = career;
        this.videoUrl = videoUrl;
    }

    public static User create(
            String email,
            String encodedPassword,
            String nickname,
            Integer age,
            Gender gender,
            String region,
            Position position,
            String skillLevel,
            String career,
            String videoUrl
    ) {
        return new User(
                email,
                encodedPassword,
                nickname,
                age,
                gender,
                region,
                position,
                skillLevel,
                career,
                videoUrl
        );
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public Integer getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public String getRegion() {
        return region;
    }

    public Position getPosition() {
        return position;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public String getCareer() {
        return career;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
