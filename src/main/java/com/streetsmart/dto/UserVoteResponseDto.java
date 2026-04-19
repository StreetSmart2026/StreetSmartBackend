package com.streetsmart.dto;

import java.time.Instant;

public class UserVoteResponseDto {

	private Long postId;
	private Long userId;
	private Boolean voted;
	private Instant voteTime;
	private Integer currentCount;

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Boolean getVoted() {
		return voted;
	}

	public void setVoted(Boolean voted) {
		this.voted = voted;
	}

	public Instant getVoteTime() {
		return voteTime;
	}

	public void setVoteTime(Instant voteTime) {
		this.voteTime = voteTime;
	}

	public Integer getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}

}
