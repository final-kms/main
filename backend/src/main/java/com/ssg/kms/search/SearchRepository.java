package com.ssg.kms.search;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssg.kms.post.Post;

@Repository
public interface SearchRepository extends JpaRepository<Post, Integer> {

	List<Post> findByTitleContaining(String searchKeyword);
	
}
