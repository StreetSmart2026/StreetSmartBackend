package com.streetsmart.dto;

public class AuthResponseDto {

	private String message;
	private UserResponseDto user;

	public AuthResponseDto() {
	}

	public AuthResponseDto(String message, UserResponseDto user) {
		this.message = message;
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserResponseDto getUser() {
		return user;
	}

	public void setUser(UserResponseDto user) {
		this.user = user;
	}

}
