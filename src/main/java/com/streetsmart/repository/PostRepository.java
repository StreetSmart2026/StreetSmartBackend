package com.streetsmart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streetsmart.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByStatus(String status);

	List<Post> findBySeverity(String severity);

}
