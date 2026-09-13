package com.matching.backend.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matching.backend.application.entity.MatchApplication;

public interface MatchApplicationRepository extends JpaRepository<MatchApplication, Long> {

    boolean existsByPost_IdAndApplicant_Id(Long postId, Long applicantId);

    List<MatchApplication> findByPost_IdOrderByCreatedAtDesc(Long postId);

    List<MatchApplication> findByApplicant_IdOrderByCreatedAtDesc(Long applicantId);
}
