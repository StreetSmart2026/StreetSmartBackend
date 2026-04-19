package com.streetsmart.dto;

import java.time.Instant;
import java.util.List;

import com.streetsmart.entity.PostPoint;

public class PostCreateRequest {

	private String postCaption;
	private String postDescription;
	private PostPoint postLocation;
	private List<PostImageRequest> postImages;
	private Instant postTime;
	private String severity;
	private String status;
	private Integer count;
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

	public List<PostImageRequest> getPostImages() {
		return postImages;
	}

	public void setPostImages(List<PostImageRequest> postImages) {
		this.postImages = postImages;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
