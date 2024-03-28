package com.ssg.kms.follow;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.user.User;
import com.ssg.kms.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;    
    
    @Transactional
    public Follow checkFollow(String email, Optional<User> user) {
    	
    	User foundUser = userRepository.findByEmail(email);
    	
    	Follow follow = followRepository.findByFollowerAndFollowee(foundUser, user.get()).orElse(null);
    	    	
    	if(follow == null) {
    		Follow newFollow = Follow.builder()
    				.follower(foundUser)
    				.followee(user.get())
        			.build();
    		
    		followRepository.save(newFollow);
    		follow = newFollow;
    		
    	} else {
    		followRepository.deleteById(follow.getId());
    	}
    	    	
    	
    	
		return follow;
    }
    
    @Transactional(readOnly = true)
    public List<Follow> readFollower(String email, Optional<User> user) {
    	return followRepository.findAllByFollowerEmail(email);
    }
    
    @Transactional(readOnly = true)
    public List<Follow> readFollowee(String email, Optional<User> user) {
    	return followRepository.findAllByFolloweeEmail(email);
    }
    
    @Transactional(readOnly = true)
    public List<Follow> readMyFollower(Optional<User> user) {
    	return followRepository.findAllByFolloweeId(user.get().getId());
    }
    
    @Transactional(readOnly = true)
    public List<Follow> readMyFollowee(Optional<User> user) {
    	return followRepository.findAllByFollowerId(user.get().getId());
    }
}
