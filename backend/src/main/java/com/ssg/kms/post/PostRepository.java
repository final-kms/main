package com.ssg.kms.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAll();

	List<Post> findAllByTitleContaining(String searchKeyword);
}
