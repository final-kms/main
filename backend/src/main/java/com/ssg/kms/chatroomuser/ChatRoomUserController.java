package com.ssg.kms.chatroomuser;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatroomuser")
public class ChatRoomUserController {
	
	private final ChatRoomUserService chatRoomUserService;
	private final UserService userService;

	// 초대하기
	@PostMapping("/create/{chatRoomId}")
	public ResponseEntity<List<ChatRoomUser>> createChatRoomUser(@PathVariable Long chatRoomId, @RequestBody ChatRoomUserDTO chatRoomUserDto) {
		return ResponseEntity.ok(chatRoomUserService.createChatRoomUser(chatRoomId, chatRoomUserDto, userService.getMyUserWithAuthorities()));
	}
	
	// 전체 가져오기
	@GetMapping("/read/{chatRoomId}")
    public ResponseEntity<List<ChatRoomUser>> readChatRoomUser(@PathVariable Long chatRoomId) {
        return ResponseEntity.ok(chatRoomUserService.readChatRoomUser(chatRoomId, userService.getMyUserWithAuthorities()));
    }
	
	// 유저가 가지고 있는 채팅룸 가져오기
	@GetMapping("/read")
    public ResponseEntity<List<ChatRoomUser>> readAllChatRoom() {
        return ResponseEntity.ok(chatRoomUserService.readAllChatRoom(userService.getMyUserWithAuthorities()));
    }
		
	// 강퇴
	@DeleteMapping("/delete/{chatRoomUserId}")
    public BodyBuilder deleteChatRoom(@PathVariable Long chatRoomUserId) {
		chatRoomUserService.deleteChatRoomUser(chatRoomUserId, userService.getMyUserWithAuthorities());
        return ResponseEntity.ok();
    }
}
