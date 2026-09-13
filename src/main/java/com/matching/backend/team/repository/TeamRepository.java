package com.matching.backend.team.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matching.backend.team.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findByOwner_IdOrderByCreatedAtDesc(Long ownerId);
}
