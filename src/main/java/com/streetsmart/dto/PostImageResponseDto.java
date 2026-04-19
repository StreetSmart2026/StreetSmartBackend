package com.streetsmart.dto;

import java.time.Instant;
import java.util.UUID;

public class PostImageResponseDto {

	private UUID postImageId;
	private String bucketId;
	private String objectPath;
	private Integer sortOrder;
	private boolean primary;
	private Instant createdAt;

	public UUID getPostImageId() {
		return postImageId;
	}

	public void setPostImageId(UUID postImageId) {
		this.postImageId = postImageId;
	}

	public String getBucketId() {
		return bucketId;
	}

	public void setBucketId(String bucketId) {
		this.bucketId = bucketId;
	}

	public String getObjectPath() {
		return objectPath;
	}

	public void setObjectPath(String objectPath) {
		this.objectPath = objectPath;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

}
