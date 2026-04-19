package com.streetsmart.entity;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long postId;

	@Column(name = "post_caption", nullable = false, length = 64)
	private String postCaption;

	@Column(name = "post_description", nullable = false, length = 2056)
	private String postDescription;

	// Keep the database column as a native Postgres point while exposing a simple JSON object in the API.
	@Convert(converter = PostPointConverter.class)
	@ColumnTransformer(write = "?::point")
	@Column(name = "post_location", nullable = false, columnDefinition = "point")
	private PostPoint postLocation;

	@Column(name = "post_image", nullable = false, unique = true)
	private UUID postImage;

	@Column(name = "post_time", nullable = false)
	private Instant postTime;

	// Each post must point to the user who created it so the app can show the author.
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private AppUser user;

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

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

}
