package com.ssg.kms.table;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
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
@RequestMapping("/api/table")
public class TableController {
	private final TableService tableService;
	private final UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<Tables> createTable(@Valid @RequestBody TableDTO tableDto) {
		return ResponseEntity.ok(tableService.createTable(tableDto, userService.getMyUserWithAuthorities()));
	}	
	
	@GetMapping("/read/{tableId}")
    public ResponseEntity<Tables> readTable(@PathVariable Long tableId) {
        return ResponseEntity.ok(tableService.readTable(tableId, userService.getMyUserWithAuthorities()));
    }

	@PutMapping("/update/{tableId}")
    public ResponseEntity<Boolean> updateTable(@PathVariable Long tableId, @Valid @RequestBody TableDTO tableDto) {
        return ResponseEntity.ok(tableService.updateTable(tableId, tableDto, userService.getMyUserWithAuthorities()));
    }
	
	@DeleteMapping("/delete/{tableId}")
    public BodyBuilder deleteTable(@PathVariable Long tableId) {
		tableService.deleteTable(tableId, userService.getMyUserWithAuthorities());
        return ResponseEntity.ok();
    }
}
