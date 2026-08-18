package com.matching.backend.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matching.backend.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findTop50ByOrderByCreatedAtDesc();

    List<Post> findByAuthor_IdOrderByCreatedAtDesc(Long authorId);
}
