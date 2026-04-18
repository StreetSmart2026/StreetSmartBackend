package com.streetsmart.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PostStatusId implements Serializable {

	@Column(name = "post_id")
	private Long postId;

	@Column(name = "status_time", nullable = false)
	private Instant statusTime;

	@Column(name = "status", nullable = false, length = 64)
	private String status;

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Instant getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Instant statusTime) {
		this.statusTime = statusTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PostStatusId)) {
			return false;
		}
		PostStatusId that = (PostStatusId) other;
		return Objects.equals(postId, that.postId)
				&& Objects.equals(statusTime, that.statusTime)
				&& Objects.equals(status, that.status);
	}

	@Override
	public int hashCode() {
		return Objects.hash(postId, statusTime, status);
	}

}
