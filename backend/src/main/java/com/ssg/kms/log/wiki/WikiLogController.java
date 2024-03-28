package com.ssg.kms.log.wiki;

import java.util.Optional;
import java.util.Set;

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
@RequestMapping("/api/wikilog")
public class WikiLogController {
	private final WikiLogService wikiLogService;
	private final UserService userService;
		
	@GetMapping("/read/{wikiLogId}")
    public ResponseEntity<WikiLog> readWikiLog(@PathVariable Long wikiLogId) {
        return ResponseEntity.ok(wikiLogService.readWikiLog(wikiLogId, userService.getMyUserWithAuthorities()));
    }
	
	@GetMapping("/readall/{wikiId}")
    public ResponseEntity<Set<WikiLog>> readWikiLogByWikiId(@PathVariable Long wikiId) {
        return ResponseEntity.ok(wikiLogService.readWikiLogByWikiId(wikiId, userService.getMyUserWithAuthorities()));
    }
		
	@DeleteMapping("/delete/{wikiLogId}")
    public ResponseEntity<WikiLog> deleteWikiLog(@PathVariable Long wikiLogId) {
        return ResponseEntity.ok(wikiLogService.deleteWikiLog(wikiLogId, userService.getMyUserWithAuthorities()));
    }
}
