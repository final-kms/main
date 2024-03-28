package com.ssg.kms.chat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.kms.chatroom.ChatRoom;
import com.ssg.kms.chatroom.ChatRoomRepository;
import com.ssg.kms.chatroomuser.ChatRoomUserRepository;
import com.ssg.kms.user.User;
import com.ssg.kms.user.UserRepository;
import com.ssg.kms.websocket.ChatWebSocketHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
	
	private final ChatRepository chatRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final ChatRoomUserRepository chatRoomUserRepository;
	
	private final UserRepository userRepository;
    @Transactional
    public Chat createChat(Long chatRoomId, ChatDTO chatDto, Optional<User> user) {
    	
    	ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();

    	Chat chat = Chat.builder()
    			.content(chatDto.getContent())
    			.date(new Date())
    			.user(user.get())
    			.chatRoom(chatRoom)
    			.build();
    	
    	chatRepository.save(chat);
    	
/// 알람 /////////////   	
    	List<Long> chatRoomUserIds = chatRoomUserRepository.findUserIdsByChatRoomId(chatRoomId);
    	List<User> users = userRepository.findAllByIdIn(chatRoomUserIds);
    	sendChat(chat, users);
/////////////////////        	
    	    	
		return chat;
    }
    

    @Transactional(readOnly = true)
    public List<Chat> readChat(Long chatRoomId, Optional<User> user) {
    	return chatRepository.findAllByChatRoomId(chatRoomId);
    }

    @Transactional
    public void deleteChat(Long chatId, Optional<User> user) {
    	chatRepository.deleteById(chatId);
    }
    
    
    
    ///// 알람 //////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    
	private final ChatWebSocketHandler chatWebSocketHandler;
	private final JSONParser jsonParser = new JSONParser();
	
	@Transactional
	public void sendChat(Chat chat, List<User> users) {
		
		for(User user : users) {
			try {
				sendChat(chat, user);
			} catch(Exception e) {
									
			}
		}
	}
	
	
	// 접속 중인 사람들에게 실시간으로 알람을 보냄.
	public void sendChat(Chat chat, User user) throws IOException {
		
		Map<String, WebSocketSession> usernameSession = chatWebSocketHandler.getUsernameSession();
		
		String chatJsonString = convertObjectToJsonString(chat);

		JSONObject chatJson;
		try {
			chatJson = (JSONObject) jsonParser.parse(chatJsonString);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"); // ISO 8601 형식 지정
			sdf.setTimeZone(TimeZone.getTimeZone("UTC")); // UTC 타임존 설정
			String isoString = sdf.format(chat.getDate()); // Date 객체를 ISO 8601 형식의 문자열로 변환

			chatJson.replace("date", isoString);

			TextMessage textMessage = new TextMessage(chatJson.toString());

			usernameSession.get(user.getUsername()).sendMessage(textMessage);
		} catch (ParseException e) {
		}

	}
	
	public String convertObjectToJsonString(Chat chat) {

		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = null;
		try {
			jsonStr = mapper.writeValueAsString(chat);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
}
