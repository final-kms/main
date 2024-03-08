package com.ssg.kms.star.post;

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
}
