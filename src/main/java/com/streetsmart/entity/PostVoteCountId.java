package com.streetsmart.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PostVoteCountId implements Serializable {

	@Column(name = "post_id")
	private Long postId;

	@Column(name = "vote_count_time", nullable = false)
	private Instant voteCountTime;

	@Column(name = "count", nullable = false)
	private Integer count;

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Instant getVoteCountTime() {
		return voteCountTime;
	}

	public void setVoteCountTime(Instant voteCountTime) {
		this.voteCountTime = voteCountTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PostVoteCountId)) {
			return false;
		}
		PostVoteCountId that = (PostVoteCountId) other;
		return Objects.equals(postId, that.postId)
				&& Objects.equals(voteCountTime, that.voteCountTime)
				&& Objects.equals(count, that.count);
	}

	@Override
	public int hashCode() {
		return Objects.hash(postId, voteCountTime, count);
	}

}
