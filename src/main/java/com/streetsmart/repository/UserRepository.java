package com.streetsmart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streetsmart.entity.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {

	boolean existsByUsername(String username);

	Optional<AppUser> findByUsername(String username);

	boolean existsByEmail(String email);

	Optional<AppUser> findByEmail(String email);

}
