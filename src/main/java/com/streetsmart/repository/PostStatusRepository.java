package com.streetsmart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streetsmart.entity.PostStatus;
import com.streetsmart.entity.PostStatusId;

public interface PostStatusRepository extends JpaRepository<PostStatus, PostStatusId> {

	List<PostStatus> findByIdStatus(String status);

	Optional<PostStatus> findTopByPostPostIdOrderByIdStatusTimeDesc(Long postId);

}
