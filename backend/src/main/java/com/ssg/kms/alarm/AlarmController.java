package com.ssg.kms.alarm;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.alarm.post.TablePostAlarmDTO;
import com.ssg.kms.alarm.reply.ReplyAlarmDTO;
import com.ssg.kms.alarm.wiki.TableWikiAlarmDTO;
import com.ssg.kms.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarm")
public class AlarmController {
	private final AlarmService alarmService;
	private final UserService userService;
	
	/////////////////////////////////////////////////
	
	@GetMapping("/read/post")
    public ResponseEntity<List<TablePostAlarmDTO>> readPostAlarm() {
        return ResponseEntity.ok(alarmService.readPostAlarm(userService.getMyUserWithAuthorities()));
    }
	
	@GetMapping("/read/wiki")
    public ResponseEntity<List<TableWikiAlarmDTO>> readWikiAlarm() {
        return ResponseEntity.ok(alarmService.readWikiAlarm(userService.getMyUserWithAuthorities()));
    }

	@GetMapping("/read/reply")
    public ResponseEntity<List<ReplyAlarmDTO>> readReplyAlarm() {
        return ResponseEntity.ok(alarmService.readReplyAlarm(userService.getMyUserWithAuthorities()));
    }

}
