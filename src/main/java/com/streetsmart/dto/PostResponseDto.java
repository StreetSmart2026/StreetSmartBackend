package com.streetsmart.dto;

import java.time.Instant;
import java.util.UUID;

import com.streetsmart.entity.PostPoint;

public class PostResponseDto {

	private Long postId;
	private String postCaption;
	private String postDescription;
	private PostPoint postLocation;
	private UUID postImage;
	private Instant postTime;
	private String severity;
	private Instant severityTime;
	private String status;
	private Instant statusTime;
	private Integer count;
	private Instant voteCountTime;
	private PostAuthorDto user;

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

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

	public Instant getSeverityTime() {
		return severityTime;
	}

	public void setSeverityTime(Instant severityTime) {
		this.severityTime = severityTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Instant getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Instant statusTime) {
		this.statusTime = statusTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Instant getVoteCountTime() {
		return voteCountTime;
	}

	public void setVoteCountTime(Instant voteCountTime) {
		this.voteCountTime = voteCountTime;
	}

	public PostAuthorDto getUser() {
		return user;
	}

	public void setUser(PostAuthorDto user) {
		this.user = user;
	}

}
