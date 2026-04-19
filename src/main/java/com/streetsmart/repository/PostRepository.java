package com.streetsmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streetsmart.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
