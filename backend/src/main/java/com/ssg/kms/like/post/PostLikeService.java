package com.ssg.kms.like.post;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.post.Post;
import com.ssg.kms.post.PostRepository;
import com.ssg.kms.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    
    @Transactional
    public PostLike checkPost(Long postId, Optional<User> user) {
    	
    	Post post = postRepository.findById(postId).get();
    	
    	PostLike postLike = postLikeRepository.findByPostAndUser(post, user.get()).orElse(null);
    	
    	if(postLike == null) {
    		PostLike newPostLike = PostLike.builder()
        			.post(post)
        			.user(user.get())
        			.build();
    		
    		postLikeRepository.save(newPostLike);
    		postLike = newPostLike;
    	} else {
    		postLikeRepository.deleteById(postLike.getId());
    	}
    	    	
    	
    	
		return postLike;
    }
    
    @Transactional(readOnly = true)
    public int readLike(Long postId, Optional<User> user) {
    	return postLikeRepository.findAllByPostId(postId).size();
    }
    
}
