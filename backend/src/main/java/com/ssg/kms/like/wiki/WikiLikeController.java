package com.ssg.kms.like.wiki;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.user.User;
import com.ssg.kms.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wikilike")
public class WikiLikeController {
	private final WikiLikeService wikiService;
	private final UserService userService;
	
	@PostMapping("/check/{wikiId}")
	public ResponseEntity checkwikiLike(@PathVariable Long wikiId) {
		Optional<User> user = userService.getMyUserWithAuthorities();
		wikiService.checkwiki(wikiId, user);
		
		return ResponseEntity.ok(wikiService.readLike(wikiId, user));
	}
	
	@GetMapping("/read/{wikiId}")
    public ResponseEntity readwikiLike(@PathVariable Long wikiId) {
		return ResponseEntity.ok(wikiService.readLike(wikiId, userService.getMyUserWithAuthorities()));
    }
}
