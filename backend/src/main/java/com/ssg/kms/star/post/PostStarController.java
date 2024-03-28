package com.ssg.kms.star.post;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.mapping.GetPostMapping;
import com.ssg.kms.user.User;
import com.ssg.kms.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/poststar")
public class PostStarController {
	private final PostStarService postService;
	private final UserService userService;
	
	@PostMapping("/check/{postId}")
	public ResponseEntity checkPostStar(@PathVariable Long postId) {
		Optional<User> user = userService.getMyUserWithAuthorities();
		postService.checkPost(postId, user);
		
		return ResponseEntity.ok(postService.readStar(postId, user));
	}
	
	@GetMapping("/read/{postId}")
    public ResponseEntity readPostStar(@PathVariable Long postId) {
		return ResponseEntity.ok(postService.readStar(postId, userService.getMyUserWithAuthorities()));
    }
	
	@GetMapping("/read/my")
    public ResponseEntity<List<GetPostMapping>> readMyPostStar() {
		return ResponseEntity.ok(postService.readMyStar(userService.getMyUserWithAuthorities()));
    }

}
