package com.ssg.kms.wiki;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
    @GetMapping("/readall")
    public ResponseEntity<Wiki> readWikiAll() {
        return (ResponseEntity)ResponseEntity.ok(wikiService.readWikiAll(userService.getMyUserWithAuthorities()));
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
}
