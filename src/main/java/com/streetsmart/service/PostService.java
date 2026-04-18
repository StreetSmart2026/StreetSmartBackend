package com.streetsmart.service;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.streetsmart.dto.PostAuthorDto;
import com.streetsmart.dto.PostCreateRequest;
import com.streetsmart.dto.PostResponseDto;
import com.streetsmart.entity.AppUser;
import com.streetsmart.entity.Post;
import com.streetsmart.entity.PostPoint;
import com.streetsmart.entity.PostSeverity;
import com.streetsmart.entity.PostSeverityId;
import com.streetsmart.entity.PostStatus;
import com.streetsmart.entity.PostStatusId;
import com.streetsmart.entity.PostVoteCount;
import com.streetsmart.entity.PostVoteCountId;
import com.streetsmart.repository.PostRepository;
import com.streetsmart.repository.PostSeverityRepository;
import com.streetsmart.repository.PostStatusRepository;
import com.streetsmart.repository.PostVoteCountRepository;
import com.streetsmart.repository.UserRepository;

@Service
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final PostSeverityRepository postSeverityRepository;
	private final PostStatusRepository postStatusRepository;
	private final PostVoteCountRepository postVoteCountRepository;

	public PostService(PostRepository postRepository, UserRepository userRepository,
			PostSeverityRepository postSeverityRepository, PostStatusRepository postStatusRepository,
			PostVoteCountRepository postVoteCountRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.postSeverityRepository = postSeverityRepository;
		this.postStatusRepository = postStatusRepository;
		this.postVoteCountRepository = postVoteCountRepository;
	}

	@Transactional
	public PostResponseDto createPost(PostCreateRequest request) {
		validateRequiredFields(request);

		Post post = new Post();
		post.setPostCaption(request.getPostCaption());
		post.setPostDescription(request.getPostDescription());
		post.setPostLocation(request.getPostLocation());
		post.setPostImage(request.getPostImage());
		post.setPostTime(request.getPostTime());
		post.setUser(resolveUser(request.getUserId()));

		Post savedPost = postRepository.save(post);
		Instant eventTime = request.getPostTime();

		postSeverityRepository.save(buildSeverity(savedPost, request.getSeverity(), eventTime));
		postStatusRepository.save(buildStatus(savedPost, request.getStatus(), eventTime));
		postVoteCountRepository.save(buildVoteCount(savedPost, request.getCount(), eventTime));

		return toPostResponse(savedPost);
	}

	public List<PostResponseDto> getAllPosts() {
		return postRepository.findAll().stream()
				.map(this::toPostResponse)
				.collect(Collectors.toList());
	}

	public PostResponseDto getPostById(Long postId) {
		return toPostResponse(postRepository.findById(postId)
				.orElseThrow(() -> new IllegalArgumentException("Post not found.")));
	}

	public List<PostResponseDto> getPostsByStatus(String status) {
		return postStatusRepository.findByIdStatus(requireValue(status, "Status is required.")).stream()
				.map(PostStatus::getPost)
				.collect(Collectors.toMap(Post::getPostId, post -> post, (left, right) -> left, LinkedHashMap::new))
				.values()
				.stream()
				.map(this::toPostResponse)
				.collect(Collectors.toList());
	}

	public List<PostResponseDto> getPostsBySeverity(String severity) {
		return postSeverityRepository.findByIdSeverity(requireValue(severity, "Severity is required.")).stream()
				.map(PostSeverity::getPost)
				.collect(Collectors.toMap(Post::getPostId, post -> post, (left, right) -> left, LinkedHashMap::new))
				.values()
				.stream()
				.map(this::toPostResponse)
				.collect(Collectors.toList());
	}

	private void validateRequiredFields(PostCreateRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("Post payload is required.");
		}

		request.setPostCaption(requireValue(request.getPostCaption(), "Post caption is required."));
		request.setPostDescription(requireValue(request.getPostDescription(), "Post description is required."));
		request.setSeverity(requireValue(request.getSeverity(), "Severity is required."));
		request.setStatus(requireValue(request.getStatus(), "Status is required."));
		request.setPostLocation(requirePoint(request.getPostLocation()));
		request.setPostImage(requireObject(request.getPostImage(), "Post image is required."));
		request.setPostTime(requireObject(request.getPostTime(), "Post time is required."));
		request.setCount(requireCount(request.getCount()));
		requireUserId(request.getUserId());
	}

	private String requireValue(String value, String message) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException(message);
		}

		return value.trim();
	}

	private PostPoint requirePoint(PostPoint point) {
		if (point == null) {
			throw new IllegalArgumentException("Post location is required.");
		}

		return point;
	}

	private <T> T requireObject(T value, String message) {
		if (value == null) {
			throw new IllegalArgumentException(message);
		}

		return value;
	}

	private Integer requireCount(Integer count) {
		if (count == null) {
			throw new IllegalArgumentException("Count is required.");
		}

		return count;
	}

	private Long requireUserId(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("User id is required.");
		}

		return userId;
	}

	private AppUser resolveUser(Long userId) {
		// Replace the incoming user stub with a managed entity so the foreign key always points to a real user.
		return userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User not found."));
	}

	public PostResponseDto toPostResponse(Post post) {
		PostResponseDto response = new PostResponseDto();
		response.setPostId(post.getPostId());
		response.setPostCaption(post.getPostCaption());
		response.setPostDescription(post.getPostDescription());
		response.setPostLocation(post.getPostLocation());
		response.setPostImage(post.getPostImage());
		response.setPostTime(post.getPostTime());
		applyLatestSeverity(response, post.getPostId());
		applyLatestStatus(response, post.getPostId());
		applyLatestVoteCount(response, post.getPostId());
		response.setUser(toPostAuthor(post.getUser()));
		return response;
	}

	private PostAuthorDto toPostAuthor(AppUser user) {
		PostAuthorDto author = new PostAuthorDto();
		author.setUserId(user.getUserId());
		author.setUsername(user.getUsername());
		author.setFirstName(user.getFirstName());
		author.setLastName(user.getLastName());
		author.setAvatar(user.getAvatar());
		return author;
	}

	private void applyLatestSeverity(PostResponseDto response, Long postId) {
		postSeverityRepository.findTopByPostPostIdOrderByIdSeverityTimeDesc(postId).ifPresent(severity -> {
			response.setSeverity(severity.getId().getSeverity());
			response.setSeverityTime(severity.getId().getSeverityTime());
		});
	}

	private void applyLatestStatus(PostResponseDto response, Long postId) {
		postStatusRepository.findTopByPostPostIdOrderByIdStatusTimeDesc(postId).ifPresent(status -> {
			response.setStatus(status.getId().getStatus());
			response.setStatusTime(status.getId().getStatusTime());
		});
	}

	private void applyLatestVoteCount(PostResponseDto response, Long postId) {
		postVoteCountRepository.findTopByPostPostIdOrderByIdVoteCountTimeDesc(postId).ifPresent(voteCount -> {
			response.setCount(voteCount.getId().getCount());
			response.setVoteCountTime(voteCount.getId().getVoteCountTime());
		});
	}

	private PostSeverity buildSeverity(Post post, String severity, Instant severityTime) {
		PostSeverityId id = new PostSeverityId();
		id.setPostId(post.getPostId());
		id.setSeverity(requireValue(severity, "Severity is required."));
		id.setSeverityTime(requireObject(severityTime, "Severity time is required."));

		PostSeverity postSeverity = new PostSeverity();
		postSeverity.setId(id);
		postSeverity.setPost(post);
		return postSeverity;
	}

	private PostStatus buildStatus(Post post, String status, Instant statusTime) {
		PostStatusId id = new PostStatusId();
		id.setPostId(post.getPostId());
		id.setStatus(requireValue(status, "Status is required."));
		id.setStatusTime(requireObject(statusTime, "Status time is required."));

		PostStatus postStatus = new PostStatus();
		postStatus.setId(id);
		postStatus.setPost(post);
		return postStatus;
	}

	private PostVoteCount buildVoteCount(Post post, Integer count, Instant voteCountTime) {
		PostVoteCountId id = new PostVoteCountId();
		id.setPostId(post.getPostId());
		id.setCount(requireCount(count));
		id.setVoteCountTime(requireObject(voteCountTime, "Vote count time is required."));

		PostVoteCount postVoteCount = new PostVoteCount();
		postVoteCount.setId(id);
		postVoteCount.setPost(post);
		return postVoteCount;
	}

}
