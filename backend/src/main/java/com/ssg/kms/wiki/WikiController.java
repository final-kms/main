package com.ssg.kms.wiki;

import java.util.List;
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

import com.ssg.kms.post.Post;
import com.ssg.kms.user.User;
import com.ssg.kms.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wiki")
public class WikiController {
	private final WikiService wikiService;
	private final UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<Wiki> createWiki(@Valid @RequestBody WikiDTO wikiDto) {
		return ResponseEntity.ok(wikiService.createWiki(wikiDto, userService.getMyUserWithAuthorities()));
	}
	
	@GetMapping("/read/{wikiId}")
    public ResponseEntity<Wiki> readWiki(@PathVariable Long wikiId) {
        return ResponseEntity.ok(wikiService.readWiki(wikiId, userService.getMyUserWithAuthorities()));
    }
	
	@PutMapping("/update/{wikiId}")
    public ResponseEntity<Wiki> updateWiki(@PathVariable Long wikiId, @Valid @RequestBody WikiDTO wikiDto) {
        return ResponseEntity.ok(wikiService.updateWiki(wikiId, wikiDto, userService.getMyUserWithAuthorities()));
    }
	
	@DeleteMapping("/delete/{wikiId}")
    public ResponseEntity<Wiki> deleteWiki(@PathVariable Long wikiId) {
        return ResponseEntity.ok(wikiService.deleteWiki(wikiId, userService.getMyUserWithAuthorities()));
    }
	
	@GetMapping("/search/{searchKeyword}")
    public ResponseEntity<List<Post>> searchPage(@PathVariable String searchKeyword) {        
        return ResponseEntity.ok(wikiService.searchPost(searchKeyword, userService.getMyUserWithAuthorities()));
    }
}
