package com.streetsmart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.streetsmart.dto.UserVoteRequest;
import com.streetsmart.dto.UserVoteResponseDto;
import com.streetsmart.service.UserVoteService;

@RestController
@RequestMapping("/api/posts/{postId}/votes")
public class UserVoteController {

	private final UserVoteService userVoteService;

	public UserVoteController(UserVoteService userVoteService) {
		this.userVoteService = userVoteService;
	}

	@PostMapping
	public ResponseEntity<UserVoteResponseDto> recordVote(@PathVariable Long postId, @RequestBody UserVoteRequest request) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userVoteService.recordVote(postId, request));
		} catch (IllegalArgumentException exception) {
			HttpStatus status = "Post not found.".equals(exception.getMessage()) || "User not found.".equals(exception.getMessage())
					? HttpStatus.NOT_FOUND
					: HttpStatus.BAD_REQUEST;
			throw new ResponseStatusException(status, exception.getMessage(), exception);
		} catch (IllegalStateException exception) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, rootMessage(exception), exception);
		}
	}

	private String rootMessage(Throwable throwable) {
		Throwable current = throwable;
		while (current.getCause() != null && current.getCause() != current) {
			current = current.getCause();
		}

		String message = current.getMessage();
		if (message == null || message.isBlank()) {
			return "Vote failed.";
		}

		return message;
	}

}
