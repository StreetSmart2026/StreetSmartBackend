package com.streetsmart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.streetsmart.dto.AuthResponseDto;
import com.streetsmart.dto.UserLoginRequest;
import com.streetsmart.dto.UserResponseDto;
import com.streetsmart.dto.UserSignupRequest;
import com.streetsmart.entity.AppUser;
import com.streetsmart.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public AppUser createUser(AppUser appUser) {
		validateRequiredFields(appUser);

		// Reject duplicate identifiers before letting the database raise a constraint error.
		if (userRepository.existsByUsername(appUser.getUsername())) {
			throw new IllegalArgumentException("Username already exists.");
		}

		if (userRepository.existsByEmail(appUser.getEmail())) {
			throw new IllegalArgumentException("Email already exists.");
		}

		return userRepository.save(appUser);
	}

	public AuthResponseDto signup(UserSignupRequest signupRequest) {
		AppUser appUser = new AppUser();
		appUser.setUsername(signupRequest.getUsername());
		appUser.setEmail(signupRequest.getEmail());
		appUser.setPhoneNumber(signupRequest.getPhoneNumber());
		appUser.setFirstName(signupRequest.getFirstName());
		appUser.setLastName(signupRequest.getLastName());
		appUser.setPasswordHash(passwordEncoder.encode(requireValue(signupRequest.getPassword(), "Password is required.")));

		return new AuthResponseDto("Signup successful.", toUserResponse(createUser(appUser)));
	}

	public AuthResponseDto login(UserLoginRequest loginRequest) {
		String email = requireValue(loginRequest.getEmail(), "Email is required.");
		String password = requireValue(loginRequest.getPassword(), "Password is required.");
		AppUser user = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

		if (!passwordEncoder.matches(password, user.getPasswordHash())) {
			throw new IllegalArgumentException("Invalid email or password.");
		}

		return new AuthResponseDto("Login successful.", toUserResponse(user));
	}

	public List<UserResponseDto> getAllUsers() {
		return userRepository.findAll().stream()
				.map(this::toUserResponse)
				.collect(Collectors.toList());
	}

	public UserResponseDto getUserById(Long userId) {
		return toUserResponse(userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User not found.")));
	}

	public UserResponseDto getUserByUsername(String username) {
		return toUserResponse(userRepository.findByUsername(requireValue(username, "Username is required."))
				.orElseThrow(() -> new IllegalArgumentException("User not found.")));
	}

	public UserResponseDto getUserByEmail(String email) {
		return toUserResponse(userRepository.findByEmail(requireValue(email, "Email is required."))
				.orElseThrow(() -> new IllegalArgumentException("User not found.")));
	}

	public UserResponseDto toUserResponse(AppUser appUser) {
		UserResponseDto response = new UserResponseDto();
		response.setUserId(appUser.getUserId());
		response.setUsername(appUser.getUsername());
		response.setEmail(appUser.getEmail());
		response.setPhoneNumber(appUser.getPhoneNumber());
		response.setFirstName(appUser.getFirstName());
		response.setLastName(appUser.getLastName());
		response.setAvatar(appUser.getAvatar());
		return response;
	}

	private void validateRequiredFields(AppUser appUser) {
		if (appUser == null) {
			throw new IllegalArgumentException("User payload is required.");
		}

		// Trim values once here so controllers and persistence always see normalized strings.
		appUser.setUsername(requireValue(appUser.getUsername(), "Username is required."));
		appUser.setEmail(requireValue(appUser.getEmail(), "Email is required."));
		appUser.setPhoneNumber(requireValue(appUser.getPhoneNumber(), "Phone number is required."));
		appUser.setFirstName(requireValue(appUser.getFirstName(), "First name is required."));
		appUser.setLastName(requireValue(appUser.getLastName(), "Last name is required."));
		appUser.setPasswordHash(requireValue(appUser.getPasswordHash(), "Password hash is required."));
	}

	private String requireValue(String value, String message) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException(message);
		}

		return value.trim();
	}

}
