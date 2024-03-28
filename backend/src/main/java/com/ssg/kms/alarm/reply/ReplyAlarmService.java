package com.ssg.kms.alarm.reply;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.kms.reply.Reply;
import com.ssg.kms.user.User;
import com.ssg.kms.websocket.NotifierWebSocketHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyAlarmService {
	
	private final ReplyAlarmRepository replyAlarmRepository;
	private final NotifierWebSocketHandler notifierWebSocketHandler;
	
	@Transactional
	public void createAlarm(Reply reply) {
				
		User user = reply.getPost().getUser();
		
		ReplyAlarm replyAlarm = ReplyAlarm.builder()
				.user(user)
				.reply(reply)
				.build();
		
		try {
			sendAlarm(reply, user);	
		} catch(Exception e) {
			
		}

		replyAlarmRepository.save(replyAlarm);
	}
	
	// 접속 중인 사람들에게 실시간으로 알람을 보냄.
	public void sendAlarm(Reply reply, User user) throws IOException {
		
		Map<String, WebSocketSession> usernameSession = notifierWebSocketHandler.getUsernameSession();
		// table1. 게시글 제목 이정도?
		
		ReplyAlarmDTO alarm = ReplyAlarmDTO.builder()
				.name("reply")
				.postTitle(reply.getPost().getTitle())
				.content(reply.getContent())
				.build();
		
		String alarmJson = convertObjectToJsonString(alarm);
		TextMessage textMessage = new TextMessage(alarmJson.toString());

		usernameSession.get(user.getUsername()).sendMessage(textMessage);
	}
	
	@Transactional
	public void deleteAlarm(List<Long> postIds, Long meId) {
		if(!postIds.isEmpty())
			replyAlarmRepository.deleteByPostIdInAndUserId(postIds, meId);
	}
	
	public String convertObjectToJsonString(ReplyAlarmDTO alarm) {

		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = null;
		try {
			jsonStr = mapper.writeValueAsString(alarm);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
	
}
