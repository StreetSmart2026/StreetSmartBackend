package com.streetsmart.controller;

import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.streetsmart.dto.FeedResponseDto;
import com.streetsmart.service.PostService;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

	private final PostService postService;

	public FeedController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping
	public FeedResponseDto getFeed(@RequestParam(defaultValue = "20") Integer limit,
			@RequestParam(required = false) Instant cursorPostTime,
			@RequestParam(required = false) Long cursorPostId,
			@RequestParam(required = false) Long viewerUserId) {
		try {
			return postService.getFeed(limit, cursorPostTime, cursorPostId, viewerUserId);
		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
		}
	}

}
