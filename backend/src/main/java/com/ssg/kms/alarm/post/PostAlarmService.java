package com.ssg.kms.alarm.post;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.kms.chat.Chat;
import com.ssg.kms.chat.ChatDTO;
import com.ssg.kms.chatroomuser.ChatRoomUser;
import com.ssg.kms.post.Post;
import com.ssg.kms.user.User;
import com.ssg.kms.websocket.NotifierWebSocketHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostAlarmService {
	
	private final PostAlarmRepository postAlarmRepository;
	private final NotifierWebSocketHandler notifierWebSocketHandler;
	
	@Transactional
	public void createAlarm(Post post, List<User> users, User me) {
		
		List<PostAlarm> postAlarms = new ArrayList<>();
		
		for(User user : users) {
			if(user.getId() != me.getId()) {
				PostAlarm postAlarm = PostAlarm.builder()
						.user(user)
						.post(post)
						.build();
				postAlarms.add(postAlarm);
				
				try {
					sendAlarm(post, user);	
				} catch(Exception e) {
					
				}
				
			}
		}
		
		postAlarmRepository.saveAll(postAlarms);
	}
	
	// 접속 중인 사람들에게 실시간으로 알람을 보냄.
	public void sendAlarm(Post post, User user) throws IOException {
		
		Map<String, WebSocketSession> usernameSession = notifierWebSocketHandler.getUsernameSession();
		// table1. 게시글 제목 이정도?
		
		TablePostAlarmDTO alarm = TablePostAlarmDTO.builder()
				.name("post")
				.tableName(post.getTable().getName())
				.postTitle(post.getTitle())
				.build();
		
		String alarmJson = convertObjectToJsonString(alarm);
		TextMessage textMessage = new TextMessage(alarmJson.toString());

		usernameSession.get(user.getUsername()).sendMessage(textMessage);
	}
	
	@Transactional
	public void deleteAlarm(List<Long> postIds, Long meId) {
		if(!postIds.isEmpty())
			postAlarmRepository.deleteByPostIdInAndUserId(postIds, meId);
	}
	
	public String convertObjectToJsonString(TablePostAlarmDTO alarm) {

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
