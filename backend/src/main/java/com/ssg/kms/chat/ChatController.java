package com.ssg.kms.chat;

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
import com.ssg.kms.chatroom.ChatRoom;
import com.ssg.kms.chatroom.ChatRoomDTO;
import com.ssg.kms.chatroom.ChatRoomService;
import com.ssg.kms.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

	private final ChatService chatService;
	private final UserService userService;

	@PostMapping("/create/{chatRoomId}")
	public ResponseEntity<Chat> createChat(@PathVariable Long chatRoomId, @RequestBody ChatDTO chatDto) {
		return ResponseEntity.ok(chatService.createChat(chatRoomId, chatDto, userService.getMyUserWithAuthorities()));
	}
	
	@GetMapping("/read/{chatRoomId}")
    public ResponseEntity<List<Chat>> readChat(@PathVariable Long chatRoomId) {
        return ResponseEntity.ok(chatService.readChat(chatRoomId, userService.getMyUserWithAuthorities()));
    }

	@DeleteMapping("/delete/{chatId}")
    public BodyBuilder deleteChatRoom(@PathVariable Long chatId) {
		chatService.deleteChat(chatId, userService.getMyUserWithAuthorities());
        return ResponseEntity.ok();
    }
}
