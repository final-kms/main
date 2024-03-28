package com.ssg.kms.tag;

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
@RequestMapping("/api/tagpost")
public class TagPostController {
	
	private final TagPostService tagPostService;
	private final UserService userService;
	
	@GetMapping("/read/{postId}")
    public ResponseEntity readTagByPostId(@PathVariable Long postId) {
		return ResponseEntity.ok(tagPostService.readTagByPostId(postId, userService.getMyUserWithAuthorities()));
    }
}
