package com.streetsmart.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.streetsmart.dto.PostCreateRequest;
import com.streetsmart.dto.PostResponseDto;
import com.streetsmart.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping
	public ResponseEntity<PostResponseDto> createPost(@RequestBody PostCreateRequest request) {
		try {
			PostResponseDto createdPost = postService.createPost(request);
			// Return the new resource location so clients can fetch it directly if needed.
			URI location = URI.create("/api/posts/" + createdPost.getPostId());
			return ResponseEntity.created(location).body(createdPost);
		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
		} catch (RuntimeException exception) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, rootMessage(exception), exception);
		}
	}

	@GetMapping
	public List<PostResponseDto> getAllPosts(@RequestParam(required = false) Long viewerUserId) {
		return postService.getAllPosts(viewerUserId);
	}

	@GetMapping("/status/{status}")
	public List<PostResponseDto> getPostsByStatus(@PathVariable String status,
			@RequestParam(required = false) Long viewerUserId) {
		try {
			return postService.getPostsByStatus(status, viewerUserId);
		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
		}
	}

	@GetMapping("/severity/{severity}")
	public List<PostResponseDto> getPostsBySeverity(@PathVariable String severity,
			@RequestParam(required = false) Long viewerUserId) {
		try {
			return postService.getPostsBySeverity(severity, viewerUserId);
		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
		}
	}

	@GetMapping("/{postId}")
	public PostResponseDto getPostById(@PathVariable Long postId, @RequestParam(required = false) Long viewerUserId) {
		try {
			return postService.getPostById(postId, viewerUserId);
		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}
	}

	private String rootMessage(Throwable throwable) {
		Throwable current = throwable;
		while (current.getCause() != null && current.getCause() != current) {
			current = current.getCause();
		}

		String message = current.getMessage();
		if (message == null || message.isBlank()) {
			return "Post creation failed.";
		}

		return message;
	}

}
