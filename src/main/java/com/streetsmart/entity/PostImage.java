package com.streetsmart.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "post_images", uniqueConstraints = {
		@UniqueConstraint(name = "uk_post_images_post_path", columnNames = { "post_id", "object_path" }),
		@UniqueConstraint(name = "uk_post_images_post_sort", columnNames = { "post_id", "sort_order" }) })
public class PostImage {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "post_image_id", nullable = false, updatable = false)
	private UUID postImageId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@Column(name = "bucket_id", nullable = false, length = 128)
	private String bucketId;

	@Column(name = "object_path", nullable = false, length = 512)
	private String objectPath;

	@Column(name = "sort_order", nullable = false)
	private Integer sortOrder;

	@Column(name = "is_primary", nullable = false)
	private boolean primary;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@PrePersist
	void prePersist() {
		if (createdAt == null) {
			createdAt = Instant.now();
		}
	}

	public UUID getPostImageId() {
		return postImageId;
	}

	public void setPostImageId(UUID postImageId) {
		this.postImageId = postImageId;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
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
