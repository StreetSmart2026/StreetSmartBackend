package com.streetsmart.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_vote")
public class UserVote {

	@EmbeddedId
	private UserVoteId id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@MapsId("postId")
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@MapsId("userId")
	@JoinColumn(name = "user_id", nullable = false)
	private AppUser user;

	public UserVoteId getId() {
		return id;
	}

	public void setId(UserVoteId id) {
		this.id = id;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

}
