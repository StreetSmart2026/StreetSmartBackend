package com.streetsmart.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streetsmart.entity.UserVote;
import com.streetsmart.entity.UserVoteId;

public interface UserVoteRepository extends JpaRepository<UserVote, UserVoteId> {

	Optional<UserVote> findTopByPostPostIdAndUserUserIdOrderByIdVoteTimeDesc(Long postId, Long userId);

	Optional<UserVote> findTopByPostPostIdAndUserUserIdAndIdVoteTimeBeforeOrderByIdVoteTimeDesc(Long postId,
			Long userId, Instant voteTime);

}
