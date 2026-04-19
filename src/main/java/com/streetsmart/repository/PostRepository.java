package com.streetsmart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.streetsmart.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	@Override
	@EntityGraph(attributePaths = "postImages")
	List<Post> findAll();

	@Override
	@EntityGraph(attributePaths = "postImages")
	Optional<Post> findById(Long postId);

}
