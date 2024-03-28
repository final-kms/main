package com.ssg.kms.tableuser;

import java.util.List;

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

import com.ssg.kms.mapping.GetTableMapping;
import com.ssg.kms.mapping.GetUserMapping;
import com.ssg.kms.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tableuser")
public class TableUserController {
	private final TableUserService tableUserService;
	private final UserService userService;
	
	//초대 - invite
	@PostMapping("/create/{tableId}")
	public ResponseEntity<Boolean> createTableUser(@PathVariable Long tableId, @RequestBody TableUserDTO tableUserDto) {
		return ResponseEntity.ok(tableUserService.createTableUser(tableId, tableUserDto, userService.getMyUserWithAuthorities()));
	}	
	
	//개별 내역 확인
	@GetMapping("/read/{tableUserId}")
    public ResponseEntity<TableUser> readTableUser(@PathVariable Long tableUserId) {
        return ResponseEntity.ok(tableUserService.readTableUser(tableUserId, userService.getMyUserWithAuthorities()));
    }
	
	//유저가 포함된 모든 테이블 출력
	@GetMapping("/read")
    public ResponseEntity<List<GetTableMapping>> readAllTableUser() {
        return ResponseEntity.ok(tableUserService.readAllTableUser(userService.getMyUserWithAuthorities()));
    }
	
	//참여한 모든 인원 확인
	@GetMapping("/readuser/{tableId}")
    public ResponseEntity<List<GetUserMapping>> readTableUsersByTable(@PathVariable Long tableId) {
        return ResponseEntity.ok(tableUserService.readTableUsersByTable(tableId, userService.getMyUserWithAuthorities()));
    }
	
	//초대 내역 확인
	@GetMapping("/invite")
    public ResponseEntity<List<TableUser>> readAllInvite() {
        return ResponseEntity.ok(tableUserService.readAllInvite(userService.getMyUserWithAuthorities()));
    }
	
	//승인 - accept 
	@PutMapping("/update/{tableUserId}")
    public ResponseEntity<TableUser> updateTableUser(@PathVariable Long tableUserId) {
        return ResponseEntity.ok(tableUserService.updateTableUser(tableUserId, userService.getMyUserWithAuthorities()));
    }
	
	//거절 - reject
	@DeleteMapping("/delete/{tableUserId}")
    public ResponseEntity<Boolean> deleteTableUser(@PathVariable Long tableUserId) {
        return ResponseEntity.ok(tableUserService.deleteTableUser(tableUserId, userService.getMyUserWithAuthorities()));
    }
	
	//추가 - admin
	@PutMapping("/admin/add/{tableId}")
    public ResponseEntity<Boolean> addAdmin(@PathVariable Long tableId, @RequestBody EmailDTO emailDto) {
        return ResponseEntity.ok(tableUserService.addAdmin(tableId, emailDto, userService.getMyUserWithAuthorities()));
    }

	//제거 - admin
	@PutMapping("/admin/remove/{tableId}")
    public ResponseEntity<Boolean> removeAdmin(@PathVariable Long tableId, @RequestBody EmailDTO emailDto) {
        return ResponseEntity.ok(tableUserService.removeAdmin(tableId, emailDto, userService.getMyUserWithAuthorities()));
    }

}
