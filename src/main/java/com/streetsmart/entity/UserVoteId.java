package com.streetsmart.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserVoteId implements Serializable {

	@Column(name = "post_id")
	private Long postId;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "voted", nullable = false)
	private Boolean voted;

	@Column(name = "vote_time", nullable = false)
	private Instant voteTime;

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

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserVoteId)) {
			return false;
		}
		UserVoteId that = (UserVoteId) other;
		return Objects.equals(postId, that.postId)
				&& Objects.equals(userId, that.userId)
				&& Objects.equals(voted, that.voted)
				&& Objects.equals(voteTime, that.voteTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(postId, userId, voted, voteTime);
	}

}
