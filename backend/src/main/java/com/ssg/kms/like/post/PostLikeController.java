package com.ssg.kms.like.post;

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
@RequestMapping("/api/postlike")
public class PostLikeController {
	private final PostLikeService postService;
	private final UserService userService;
	
	@PostMapping("/check/{postId}")
	public ResponseEntity checkPostLike(@PathVariable Long postId) {
		Optional<User> user = userService.getMyUserWithAuthorities();
		postService.checkPost(postId, user);
		
		return ResponseEntity.ok(postService.readLike(postId, user));
	}
	
	@GetMapping("/read/{postId}")
    public ResponseEntity readPostLike(@PathVariable Long postId) {
		return ResponseEntity.ok(postService.readLike(postId, userService.getMyUserWithAuthorities()));
    }
}
