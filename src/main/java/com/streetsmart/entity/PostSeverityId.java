package com.streetsmart.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PostSeverityId implements Serializable {

	@Column(name = "post_id")
	private Long postId;

	@Column(name = "severity_time", nullable = false)
	private Instant severityTime;

	@Column(name = "severity", nullable = false, length = 64)
	private String severity;

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Instant getSeverityTime() {
		return severityTime;
	}

	public void setSeverityTime(Instant severityTime) {
		this.severityTime = severityTime;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PostSeverityId that)) {
			return false;
		}
        return Objects.equals(postId, that.postId)
				&& Objects.equals(severityTime, that.severityTime)
				&& Objects.equals(severity, that.severity);
	}

	@Override
	public int hashCode() {
		return Objects.hash(postId, severityTime, severity);
	}

}
