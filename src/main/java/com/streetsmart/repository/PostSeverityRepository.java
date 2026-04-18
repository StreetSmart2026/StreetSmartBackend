package com.streetsmart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streetsmart.entity.PostSeverity;
import com.streetsmart.entity.PostSeverityId;

public interface PostSeverityRepository extends JpaRepository<PostSeverity, PostSeverityId> {

	List<PostSeverity> findByIdSeverity(String severity);

	Optional<PostSeverity> findTopByPostPostIdOrderByIdSeverityTimeDesc(Long postId);

}
