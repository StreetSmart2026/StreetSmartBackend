package com.streetsmart.dto;

import java.time.Instant;

public class UserVoteRequest {

	private Long userId;
	private Boolean voted;
	private Instant voteTime;

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

}
