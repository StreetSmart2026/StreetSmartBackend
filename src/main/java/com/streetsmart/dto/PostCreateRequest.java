package com.streetsmart.dto;

import java.time.Instant;
import java.util.UUID;

import com.streetsmart.entity.PostPoint;

public class PostCreateRequest {

	private String postCaption;
	private String postDescription;
	private PostPoint postLocation;
	private UUID postImage;
	private Instant postTime;
	private String severity;
	private String status;
	private Long userId;

	public String getPostCaption() {
		return postCaption;
	}

	public void setPostCaption(String postCaption) {
		this.postCaption = postCaption;
	}

	public String getPostDescription() {
		return postDescription;
	}

	public void setPostDescription(String postDescription) {
		this.postDescription = postDescription;
	}

	public PostPoint getPostLocation() {
		return postLocation;
	}

	public void setPostLocation(PostPoint postLocation) {
		this.postLocation = postLocation;
	}

	public UUID getPostImage() {
		return postImage;
	}

	public void setPostImage(UUID postImage) {
		this.postImage = postImage;
	}

	public Instant getPostTime() {
		return postTime;
	}

	public void setPostTime(Instant postTime) {
		this.postTime = postTime;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
