package com.ssg.kms.post;

import java.util.Optional;

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
@RequestMapping("/api/post")
public class PostController {
	private final PostService postService;
	private final UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<Post> createPost(@Valid @RequestBody PostDTO PostDto) {
		return ResponseEntity.ok(postService.createPost(PostDto, userService.getMyUserWithAuthorities()));
	}
	
	@GetMapping("/read/{postId}")
    public ResponseEntity<Post> readPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.readPost(postId, userService.getMyUserWithAuthorities()));
    }
	
	@PutMapping("/update/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @Valid @RequestBody PostDTO postDto) {
        return ResponseEntity.ok(postService.updatePost(postId, postDto, userService.getMyUserWithAuthorities()));
    }
	
	@DeleteMapping("/delete/{postId}")
    public ResponseEntity<Post> deletePost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.deletePost(postId, userService.getMyUserWithAuthorities()));
    }
}
