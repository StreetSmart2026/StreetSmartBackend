package com.streetsmart.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.streetsmart.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllByOrderByPostTimeDescPostIdDesc(Pageable pageable);

	@Query("""
			select p
			from Post p
			where p.postTime < :cursorPostTime
				or (p.postTime = :cursorPostTime and p.postId < :cursorPostId)
			order by p.postTime desc, p.postId desc
			""")
	List<Post> findFeedPageBeforeCursor(@Param("cursorPostTime") Instant cursorPostTime,
			@Param("cursorPostId") Long cursorPostId, Pageable pageable);

}
