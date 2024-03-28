package com.ssg.kms.like.reply;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssg.kms.mapping.GetPostMapping;
import com.ssg.kms.reply.Reply;
import com.ssg.kms.user.User;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {

	Optional<ReplyLike> findByReplyAndUser(Reply reply, User user);

	Set<ReplyLike> findAllByReplyId(Long replyId);

//	List<GetPostMapping> findPostAllByUserId(Long id);
}
