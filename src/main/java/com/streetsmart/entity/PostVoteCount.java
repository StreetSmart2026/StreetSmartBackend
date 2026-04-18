package com.streetsmart.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_vote_count")
public class PostVoteCount {

	@EmbeddedId
	private PostVoteCountId id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@MapsId("postId")
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	public PostVoteCountId getId() {
		return id;
	}

	public void setId(PostVoteCountId id) {
		this.id = id;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}
