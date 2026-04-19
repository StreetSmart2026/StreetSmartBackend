package com.streetsmart.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.streetsmart.dto.UserVoteRequest;
import com.streetsmart.dto.UserVoteResponseDto;
import com.streetsmart.entity.AppUser;
import com.streetsmart.entity.Post;
import com.streetsmart.entity.PostVoteCount;
import com.streetsmart.entity.PostVoteCountId;
import com.streetsmart.entity.UserVote;
import com.streetsmart.entity.UserVoteId;
import com.streetsmart.repository.PostRepository;
import com.streetsmart.repository.PostVoteCountRepository;
import com.streetsmart.repository.UserRepository;
import com.streetsmart.repository.UserVoteRepository;

@Service
public class UserVoteService {

	private final UserVoteRepository userVoteRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final PostVoteCountRepository postVoteCountRepository;

	public UserVoteService(UserVoteRepository userVoteRepository, PostRepository postRepository,
			UserRepository userRepository, PostVoteCountRepository postVoteCountRepository) {
		this.userVoteRepository = userVoteRepository;
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.postVoteCountRepository = postVoteCountRepository;
	}

	@Transactional
	public UserVoteResponseDto recordVote(Long postId, UserVoteRequest request) {
		Long requiredPostId = requirePostId(postId);
		UserVoteRequest validatedRequest = requireRequest(request);
		Long userId = requireUserId(validatedRequest.getUserId());
		boolean voted = requireVoted(validatedRequest.getVoted());
		Instant voteTime = resolveVoteTime(requiredPostId, validatedRequest.getVoteTime());

		Post post = postRepository.findById(requiredPostId)
				.orElseThrow(() -> new IllegalArgumentException("Post not found."));
		AppUser user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User not found."));
		Optional<UserVote> previousVote = userVoteRepository
				.findTopByPostPostIdAndUserUserIdAndIdVoteTimeBeforeOrderByIdVoteTimeDesc(requiredPostId, userId, voteTime);

		UserVote savedVote = saveVote(post, user, voted, voteTime);
		Integer currentCount = saveVoteCountSnapshot(post, previousVote, voted, voteTime);
		return toResponse(savedVote, currentCount);
	}

	private UserVote saveVote(Post post, AppUser user, boolean voted, Instant voteTime) {
		UserVoteId id = new UserVoteId();
		id.setPostId(post.getPostId());
		id.setUserId(user.getUserId());
		id.setVoted(voted);
		id.setVoteTime(voteTime);

		UserVote userVote = new UserVote();
		userVote.setId(id);
		userVote.setPost(post);
		userVote.setUser(user);

		try {
			return userVoteRepository.save(userVote);
		} catch (RuntimeException exception) {
			throw new IllegalStateException("Failed to save user vote: " + rootMessage(exception), exception);
		}
	}

	private Integer saveVoteCountSnapshot(Post post, Optional<UserVote> previousVote, boolean voted, Instant voteTime) {
		int currentCount = postVoteCountRepository.findTopByPostPostIdOrderByIdVoteCountTimeDesc(post.getPostId())
				.map(voteCount -> voteCount.getId().getCount())
				.orElse(0);

		int nextCount = currentCount;
		if (previousVote.isEmpty()) {
			if (voted) {
				nextCount = currentCount + 1;
			}
		} else if (Boolean.TRUE.equals(previousVote.get().getId().getVoted()) != voted) {
			nextCount = voted ? currentCount + 1 : currentCount - 1;
		}

		PostVoteCountId voteCountId = new PostVoteCountId();
		voteCountId.setPostId(post.getPostId());
		voteCountId.setCount(Math.max(nextCount, 0));
		voteCountId.setVoteCountTime(voteTime);

		PostVoteCount voteCount = new PostVoteCount();
		voteCount.setId(voteCountId);
		voteCount.setPost(post);

		try {
			postVoteCountRepository.save(voteCount);
			return voteCountId.getCount();
		} catch (RuntimeException exception) {
			throw new IllegalStateException("Failed to save post vote count state: " + rootMessage(exception), exception);
		}
	}

	private Instant resolveVoteTime(Long postId, Instant requestedVoteTime) {
		Instant voteTime = requestedVoteTime == null ? Instant.now() : requestedVoteTime;
		postVoteCountRepository.findTopByPostPostIdOrderByIdVoteCountTimeDesc(postId)
				.map(voteCount -> voteCount.getId().getVoteCountTime())
				.filter(latestVoteTime -> voteTime.isBefore(latestVoteTime))
				.ifPresent(latestVoteTime -> {
					throw new IllegalArgumentException("Vote time must be after the latest recorded vote change.");
				});
		return voteTime;
	}

	private UserVoteResponseDto toResponse(UserVote userVote, Integer currentCount) {
		UserVoteResponseDto response = new UserVoteResponseDto();
		response.setPostId(userVote.getPost().getPostId());
		response.setUserId(userVote.getUser().getUserId());
		response.setVoted(userVote.getId().getVoted());
		response.setVoteTime(userVote.getId().getVoteTime());
		response.setCurrentCount(currentCount);
		return response;
	}

	private UserVoteRequest requireRequest(UserVoteRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("Vote payload is required.");
		}

		return request;
	}

	private Long requirePostId(Long postId) {
		if (postId == null) {
			throw new IllegalArgumentException("Post id is required.");
		}

		return postId;
	}

	private Long requireUserId(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("User id is required.");
		}

		return userId;
	}

	private boolean requireVoted(Boolean voted) {
		if (voted == null) {
			throw new IllegalArgumentException("Voted is required.");
		}

		return voted;
	}

	private String rootMessage(Throwable throwable) {
		Throwable current = throwable;
		while (current.getCause() != null && current.getCause() != current) {
			current = current.getCause();
		}

		String message = current.getMessage();
		if (message == null || message.isBlank()) {
			return throwable.getClass().getSimpleName();
		}

		return message;
	}

}
