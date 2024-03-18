package com.ssg.kms.star.post;

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
public class PostStarService {
    private final PostStarRepository postStarRepository;
    private final PostRepository postRepository;
    
    @Transactional
    public PostStar checkPost(Long postId, Optional<User> user) {
    	
    	Post post = postRepository.findById(postId).get();
    	
    	PostStar postStar = postStarRepository.findByPostAndUser(post, user.get()).orElse(null);
    	
    	if(postStar == null) {
    		PostStar newPostStar = PostStar.builder()
        			.post(post)
        			.user(user.get())
        			.build();
    		
    		postStarRepository.save(newPostStar);
    		postStar = newPostStar;
    	} else {
    		postStarRepository.deleteById(postStar.getId());
    	}
    	    	
    	
    	
		return postStar;
    }
    
    @Transactional(readOnly = true)
    public int readStar(Long postId, Optional<User> user) {
    	return postStarRepository.findAllByPostId(postId).size();
    }
    
}
