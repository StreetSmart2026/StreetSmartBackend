package com.streetsmart;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.streetsmart.repository.PostRepository;
import com.streetsmart.repository.PostSeverityRepository;
import com.streetsmart.repository.PostStatusRepository;
import com.streetsmart.repository.PostVoteCountRepository;
import com.streetsmart.repository.UserRepository;
import com.streetsmart.repository.UserVoteRepository;

@SpringBootTest(properties = {
		"spring.autoconfigure.exclude="
				+ "org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration,"
				+ "org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration,"
				+ "org.springframework.boot.data.jpa.autoconfigure.JpaRepositoriesAutoConfiguration,"
				+ "org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration,"
				+ "org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration" })
class StreetSmartApplicationTests {

	// Mock the repository so the context test does not require a live database connection.
	@MockitoBean
	private PostRepository postRepository;

	@MockitoBean
	private PostSeverityRepository postSeverityRepository;

	@MockitoBean
	private PostStatusRepository postStatusRepository;

	@MockitoBean
	private PostVoteCountRepository postVoteCountRepository;

	@MockitoBean
	private UserRepository userRepository;

	@MockitoBean
	private UserVoteRepository userVoteRepository;

	@Test
	void contextLoads() {
	}

}
