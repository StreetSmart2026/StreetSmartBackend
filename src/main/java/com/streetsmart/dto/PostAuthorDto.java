package com.streetsmart.dto;

import java.util.UUID;

public class PostAuthorDto {

	private Long userId;
	private String username;
	private String firstName;
	private String lastName;
	private UUID avatar;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UUID getAvatar() {
		return avatar;
	}

	public void setAvatar(UUID avatar) {
		this.avatar = avatar;
	}

}
