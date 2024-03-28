package com.ssg.kms.tag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssg.kms.mapping.GetPostMapping;

public interface TagPostRepository extends JpaRepository<TagPost, Long> {

	List<GetPostMapping> findAllByTagId(Long id);

	void deleteAllByPostId(Long postId);

	List<TagPost> findAllByPostId(Long postId);


}
