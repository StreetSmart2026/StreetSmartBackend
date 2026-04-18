package com.streetsmart.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.streetsmart.dto.AuthResponseDto;
import com.streetsmart.dto.UserLoginRequest;
import com.streetsmart.dto.UserSignupRequest;
import com.streetsmart.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/signup")
	public ResponseEntity<AuthResponseDto> signup(@RequestBody UserSignupRequest signupRequest) {
		try {
			AuthResponseDto response = userService.signup(signupRequest);
			Long userId = response.getUser().getUserId();
			return ResponseEntity.created(URI.create("/api/users/" + userId)).body(response);
		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
		}
	}

	@PostMapping("/login")
	public AuthResponseDto login(@RequestBody UserLoginRequest loginRequest) {
		try {
			return userService.login(loginRequest);
		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, exception.getMessage(), exception);
		}
	}

}
