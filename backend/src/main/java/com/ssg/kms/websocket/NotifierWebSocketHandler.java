package com.ssg.kms.websocket;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.kms.chat.Chat;
import com.ssg.kms.chat.ChatDTO;
import com.ssg.kms.chat.ChatService;
import com.ssg.kms.chatroomuser.ChatRoomUser;
import com.ssg.kms.chatroomuser.ChatRoomUserService;
import com.ssg.kms.security.TokenProvider;
import com.ssg.kms.user.User;
import com.ssg.kms.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotifierWebSocketHandler extends TextWebSocketHandler {

	private Map<WebSocketSession, String> sessionUsername = new HashMap<>();
	private Map<String, WebSocketSession> usernameSession = new HashMap<>();

	private final TokenProvider tokenProvider;
	private final JSONParser jsonParser = new JSONParser();

	public Map<String, WebSocketSession> getUsernameSession(){
		return usernameSession;
	}
	
	public Map<WebSocketSession, String> getSessionUsername(){
		return sessionUsername;
	}

	
	// 연결시
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessionUsername.put(session, null);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String username = sessionUsername.get(session);
		sessionUsername.remove(session);
		usernameSession.remove(username);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		JSONObject jsonObj = (JSONObject) jsonParser.parse(message.getPayload());
		String username;
		String auth;
		try {
			auth = (String) jsonObj.get("auth");
			username = getUserNameByMessageWithJwt(auth);
			sessionUsername.replace(session, username);
			usernameSession.put(username, session);
			return;
		} catch (Exception e) {

		}
	}

	private String getUserNameByMessageWithJwt(String auth) {
		Authentication authentication = tokenProvider.getAuthentication(auth);
		String username = authentication.getName();
		return username;
	}

}
