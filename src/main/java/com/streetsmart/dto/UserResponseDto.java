package com.streetsmart.dto;

import java.util.UUID;

public class UserResponseDto {

	private Long userId;
	private String username;
	private String email;
	private String phoneNumber;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
