package com.ssg.kms.star.wiki;

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
@RequestMapping("/api/wikistar")
public class WikiStarController {
	private final WikiStarService wikiService;
	private final UserService userService;
	
	@PostMapping("/check/{wikiId}")
	public ResponseEntity checkWikiStar(@PathVariable Long wikiId) {
		Optional<User> user = userService.getMyUserWithAuthorities();
		wikiService.checkWiki(wikiId, user);
		
		return ResponseEntity.ok(wikiService.readStar(wikiId, user));
	}
	
	@GetMapping("/read/{wikiId}")
    public ResponseEntity readWikiStar(@PathVariable Long wikiId) {
		return ResponseEntity.ok(wikiService.readStar(wikiId, userService.getMyUserWithAuthorities()));
    }
}
