package com.streetsmart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streetsmart.entity.PostVoteCount;
import com.streetsmart.entity.PostVoteCountId;

public interface PostVoteCountRepository extends JpaRepository<PostVoteCount, PostVoteCountId> {

	Optional<PostVoteCount> findTopByPostPostIdOrderByIdVoteCountTimeDesc(Long postId);

}
