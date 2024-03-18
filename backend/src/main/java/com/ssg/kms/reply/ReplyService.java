package com.ssg.kms.reply;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.post.Post;
import com.ssg.kms.post.PostRepository;
import com.ssg.kms.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    
    @Transactional
    public Reply createReply(ReplyDTO replyDto, Optional<User> user, Long postId) {
    	Optional<Post> postOptional = postRepository.findById(postId);
    	Post post = postOptional.orElseThrow(() -> new IllegalArgumentException("Post not found"));
    	Reply reply = Reply.builder()
    			.content(replyDto.getContent())
    			.date(new Date())
    			.user(user.get())
    			.post(post)
    			.build();
    	
		return replyRepository.save(reply);
    }
    
    @Transactional(readOnly = true)
    public Reply readReply(Long replyId, Optional<User> user) {
		return replyRepository.findById(replyId).get();
    }
    
    @Transactional
    public Reply updateReply(Long replyId, ReplyDTO replyDto, Optional<User> user) {
    	Reply reply = replyRepository.findById(replyId).get();
    	
    	reply.setContent(replyDto.getContent());
    	reply.setDate(new Date());
    	
		return replyRepository.save(reply);
    }

    @Transactional
    public Reply deleteReply(Long replyId, Optional<User> user) {
    	Reply reply = replyRepository.findById(replyId).get();
    	reply.setUser(null);
    	reply.setPost(null);
    	replyRepository.deleteById(replyId);
    	reply.setUser(user.get());
    	return reply;
    }

    @Transactional(readOnly = true)
    public List<Reply> readAllReply(Long postId, Optional<User> user) {
		return replyRepository.findAllByPostId(postId);
    }
}
