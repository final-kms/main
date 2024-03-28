package com.ssg.kms.reply;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssg.kms.mapping.GetPostMapping;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

	List<Reply> findAllByPostId(Long postId);

	List<Reply> findAllByUserId(Long id);

	Set<GetPostMapping> findPostAllByUserId(Long id);
	
	
	
}