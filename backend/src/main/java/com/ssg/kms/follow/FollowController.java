package com.ssg.kms.follow;

import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.user.User;
import com.ssg.kms.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowController {

	private final FollowService followService;
	private final UserService userService;
	
	@PostMapping("/check/{email}")
	public ResponseEntity checkFollow(@PathVariable String email) {

		Optional<User> user = userService.getMyUserWithAuthorities();
		followService.checkFollow(email, user);
		
		return ResponseEntity.ok(followService.readFollower(email, user));
	}
	
	@GetMapping("/read/follower/{email}")
    public ResponseEntity readFollower(@PathVariable String email) {
		return ResponseEntity.ok(followService.readFollower(email, userService.getMyUserWithAuthorities()));
    }
	
	@GetMapping("/read/followee/{email}")
    public ResponseEntity readFollowee(@PathVariable String email) {
		return ResponseEntity.ok(followService.readFollowee(email, userService.getMyUserWithAuthorities()));
    }
	
	@GetMapping("/read/follower")
    public ResponseEntity readMyFollower() {
		return ResponseEntity.ok(followService.readMyFollower(userService.getMyUserWithAuthorities()));
    }
	
	@GetMapping("/read/followee")
    public ResponseEntity readMyFollowee() {
		return ResponseEntity.ok(followService.readMyFollowee(userService.getMyUserWithAuthorities()));
    }


}
