package com.streetsmart.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.streetsmart.dto.UserResponseDto;
import com.streetsmart.dto.UserSignupRequest;
import com.streetsmart.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	// Keep this route as a compatibility alias while signup moves to the dedicated auth controller.
	@PostMapping
	public ResponseEntity<UserResponseDto> createUser(@RequestBody UserSignupRequest signupRequest) {
		try {
			UserResponseDto createdUser = userService.signup(signupRequest).getUser();
			// Return the new resource location so clients can fetch it directly if needed.
			URI location = URI.create("/api/users/" + createdUser.getUserId());
			return ResponseEntity.created(location).body(createdUser);
		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
		}
	}

	@GetMapping
	public List<UserResponseDto> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/username/{username}")
	public UserResponseDto getUserByUsername(@PathVariable String username) {
		try {
			return userService.getUserByUsername(username);
		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}
	}

	@GetMapping("/email/{email}")
	public UserResponseDto getUserByEmail(@PathVariable String email) {
		try {
			return userService.getUserByEmail(email);
		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}
	}

	@GetMapping("/{userId}")
	public UserResponseDto getUserById(@PathVariable Long userId) {
		try {
			return userService.getUserById(userId);
		} catch (IllegalArgumentException exception) {
			// Missing records map to 404 instead of exposing service-layer exceptions to clients.
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}
	}

}
