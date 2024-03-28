package com.ssg.kms.like.reply;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.mapping.GetPostMapping;
import com.ssg.kms.reply.Reply;
import com.ssg.kms.reply.ReplyRepository;
import com.ssg.kms.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyLikeService {
    private final ReplyLikeRepository replyLikeRepository;
    private final ReplyRepository replyRepository;
    
    @Transactional
    public ReplyLike checkReply(Long replyId, Optional<User> user) {
    	
    	Reply reply = replyRepository.findById(replyId).get();
    	
    	ReplyLike replyLike = replyLikeRepository.findByReplyAndUser(reply, user.get()).orElse(null);
    	
    	if(replyLike == null) {
    		ReplyLike newReplyLike = ReplyLike.builder()
        			.reply(reply)
        			.user(user.get())
        			.build();
    		
    		replyLikeRepository.save(newReplyLike);
    		replyLike = newReplyLike;
    	} else {
    		replyLikeRepository.deleteById(replyLike.getId());
    	}
    	    	
    	
    	
		return replyLike;
    }
    
    @Transactional(readOnly = true)
    public int readLike(Long replyId, Optional<User> user) {
    	return replyLikeRepository.findAllByReplyId(replyId).size();
    }

//    @Transactional(readOnly = true)
//    public List<GetPostMapping> readMyLike(Optional<User> user) {
//    	return replyLikeRepository.findPostAllByUserId(user.get().getId());
//    }

}
