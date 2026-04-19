package com.streetsmart.dto;

import java.time.Instant;

public class FeedCursorDto {

	private Instant postTime;
	private Long postId;

	public Instant getPostTime() {
		return postTime;
	}

	public void setPostTime(Instant postTime) {
		this.postTime = postTime;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

}
