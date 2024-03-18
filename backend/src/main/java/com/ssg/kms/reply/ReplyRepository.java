package com.ssg.kms.reply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssg.kms.post.Post;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

	List<Reply> findAllByPostId(Long postId);
	
}