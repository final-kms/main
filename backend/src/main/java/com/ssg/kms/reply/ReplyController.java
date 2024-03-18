package com.ssg.kms.reply;

import java.util.List;

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
@RequestMapping("/api/reply")
public class ReplyController {
	private final ReplyService replyService;
	private final UserService userService;
	
	@PostMapping("/create/{postId}")
	public ResponseEntity<Reply> createReply(@Valid @RequestBody ReplyDTO replyDto, @PathVariable Long postId) {
		return ResponseEntity.ok(replyService.createReply(replyDto, userService.getMyUserWithAuthorities(), postId));
	}	
	
	@GetMapping("/read/{replyId}")
    public ResponseEntity<Reply> readReply(@PathVariable Long replyId) {
        return ResponseEntity.ok(replyService.readReply(replyId, userService.getMyUserWithAuthorities()));
    }
	
	@GetMapping("/readpost/{postId}")
    public ResponseEntity<List<Reply>> readAllReply(@PathVariable Long postId) {
        return ResponseEntity.ok(replyService.readAllReply(postId, userService.getMyUserWithAuthorities()));
    }
	
	@PutMapping("/update/{replyId}")
    public ResponseEntity<Reply> updateReply(@PathVariable Long replyId, @Valid @RequestBody ReplyDTO replyDto) {
        return ResponseEntity.ok(replyService.updateReply(replyId, replyDto, userService.getMyUserWithAuthorities()));
    }
	
	@DeleteMapping("/delete/{replyId}")
    public ResponseEntity<Reply> deleteReply(@PathVariable Long replyId) {
        return ResponseEntity.ok(replyService.deleteReply(replyId, userService.getMyUserWithAuthorities()));
    }
}
