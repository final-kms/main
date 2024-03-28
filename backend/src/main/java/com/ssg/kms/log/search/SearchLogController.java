package com.ssg.kms.log.search;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/searchlog")
public class SearchLogController {
	private final SearchLogService searchLogService;
	private final UserService userService;
	
	@GetMapping("/read")
	public ResponseEntity<SearchLog> readSearchLog(){
		return ResponseEntity.ok(searchLogService.readSearchLog(userService.getMyUserWithAuthorities()));
	}
}
