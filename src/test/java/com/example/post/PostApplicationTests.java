package com.example.post;

import com.example.post.domain.post.entity.Post;
import com.example.post.domain.post.repository.PostRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostApplicationTests {

	@Autowired
	private PostRepository postRepository;

	@Test
	void postCreate() {
		Post post1 = Post.of("제목1", "본문1");
		this.postRepository.save(post1);

		Post post2 = Post.of("제목2", "본문2");
		this.postRepository.save(post2);

	}

//	@Test
//	void postIndex() {
//		List<Post> posts = postRepository.findAll();
//		Assertions.assertEquals(2, posts.size());
//
//		Post post = posts.get(0);
//		Assertions.assertEquals("제목1", post.getTitle());
//	}

	@Test
	void postShow() {
		Optional<Post> optionalPost = postRepository.findById(1L);
		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();
			Assertions.assertEquals("제목1", post.getTitle());
		}
	}
}
