package com.matching.backend.team.entity;

import com.matching.backend.common.entity.BaseTimeEntity;
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

@Entity
@Table(name = "teams")
public class Team extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_user_id", nullable = false)
    private User owner;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String logoUrl;

    @Column(nullable = false, length = 100)
    private String homeRegion;

    @Column(length = 100)
    private String homeStadium;

    @Column(nullable = false, length = 50)
    private String ageGroup;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TeamLevel level;

    private Integer fee;

    protected Team() {
    }

    private Team(
            User owner,
            String name,
            String logoUrl,
            String homeRegion,
            String homeStadium,
            String ageGroup,
            TeamLevel level,
            Integer fee
    ) {
        this.owner = owner;
        this.name = name;
        this.logoUrl = logoUrl;
        this.homeRegion = homeRegion;
        this.homeStadium = homeStadium;
        this.ageGroup = ageGroup;
        this.level = level;
        this.fee = fee;
    }

    public static Team create(
            User owner,
            String name,
            String logoUrl,
            String homeRegion,
            String homeStadium,
            String ageGroup,
            TeamLevel level,
            Integer fee
    ) {
        return new Team(owner, name, logoUrl, homeRegion, homeStadium, ageGroup, level, fee);
    }

    public void update(
            String name,
            String logoUrl,
            String homeRegion,
            String homeStadium,
            String ageGroup,
            TeamLevel level,
            Integer fee
    ) {
        if (name != null) {
            this.name = name;
        }
        if (logoUrl != null) {
            this.logoUrl = logoUrl;
        }
        if (homeRegion != null) {
            this.homeRegion = homeRegion;
        }
        if (homeStadium != null) {
            this.homeStadium = homeStadium;
        }
        if (ageGroup != null) {
            this.ageGroup = ageGroup;
        }
        if (level != null) {
            this.level = level;
        }
        if (fee != null) {
            this.fee = fee;
        }
    }

    public boolean isOwnedBy(Long userId) {
        return owner.getId().equals(userId);
    }

    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getHomeRegion() {
        return homeRegion;
    }

    public String getHomeStadium() {
        return homeStadium;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public TeamLevel getLevel() {
        return level;
    }

    public Integer getFee() {
        return fee;
    }
}
