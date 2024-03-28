package com.ssg.kms.chatroom;

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

import com.ssg.kms.category.CategoryService;
import com.ssg.kms.post.Post;
import com.ssg.kms.post.PostDTO;
import com.ssg.kms.post.PostService;
import com.ssg.kms.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatroom")
public class ChatRoomController {
	
	private final ChatRoomService chatRoomService;
	private final UserService userService;

	@PostMapping("/create")
	public ResponseEntity<ChatRoom> createChatRoom(@RequestBody ChatRoomDTO chatRoomDto) {
		return ResponseEntity.ok(chatRoomService.createChatRoom(chatRoomDto, userService.getMyUserWithAuthorities()));
	}
	
	@GetMapping("/read/{chatRoomId}")
    public ResponseEntity<ChatRoom> readChatRoom(@PathVariable Long chatRoomId) {
        return ResponseEntity.ok(chatRoomService.readChatRoom(chatRoomId, userService.getMyUserWithAuthorities()));
    }
	
	@PutMapping("/update/{chatRoomId}")
    public ResponseEntity<ChatRoom> updateChatRoom(@PathVariable Long chatRoomId, @RequestBody ChatRoomDTO chatRoomDto) {
        return ResponseEntity.ok(chatRoomService.updateChatRoom(chatRoomId, chatRoomDto, userService.getMyUserWithAuthorities()));
    }
	
	@DeleteMapping("/delete/{chatRoomId}")
    public BodyBuilder deleteChatRoom(@PathVariable Long chatRoomId) {
		chatRoomService.deleteChatRoom(chatRoomId, userService.getMyUserWithAuthorities());
        return ResponseEntity.ok();
    }
}
