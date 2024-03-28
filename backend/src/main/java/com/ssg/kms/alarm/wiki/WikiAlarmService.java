package com.ssg.kms.alarm.wiki;

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
import com.ssg.kms.user.User;
import com.ssg.kms.websocket.NotifierWebSocketHandler;
import com.ssg.kms.wiki.Wiki;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WikiAlarmService {
	
	private final WikiAlarmRepository wikiAlarmRepository;
	private final NotifierWebSocketHandler notifierWebSocketHandler;
	
	@Transactional
	public void createAlarm(Wiki wiki, List<User> users, User me) {
		
		List<WikiAlarm> wikiAlarms = new ArrayList<>();
		
		for(User user : users) {
			if(user.getId() != me.getId()) {
				WikiAlarm wikiAlarm = WikiAlarm.builder()
						.user(user)
						.wiki(wiki)
						.build();
				wikiAlarms.add(wikiAlarm);
				
				try {
					sendAlarm(wiki, user);	
				} catch(Exception e) {
					
				}
				
			}
		}
		
		wikiAlarmRepository.saveAll(wikiAlarms);
	}
	
	// 접속 중인 사람들에게 실시간으로 알람을 보냄.
	public void sendAlarm(Wiki wiki, User user) throws IOException {
		
		Map<String, WebSocketSession> usernameSession = notifierWebSocketHandler.getUsernameSession();
		// table1. 게시글 제목 이정도?
		
		TableWikiAlarmDTO alarm = TableWikiAlarmDTO.builder()
				.name("wiki")
				.tableName(wiki.getTable().getName())
				.wikiTitle(wiki.getTitle())
				.build();
		
		String alarmJson = convertObjectToJsonString(alarm);
		TextMessage textMessage = new TextMessage(alarmJson.toString());

		usernameSession.get(user.getUsername()).sendMessage(textMessage);
	}
	
	@Transactional
	public void deleteAlarm(List<Long> wikiIds, Long meId) {
		if(!wikiIds.isEmpty())
			wikiAlarmRepository.deleteByWikiIdInAndUserId(wikiIds, meId);
	}
	
	public String convertObjectToJsonString(TableWikiAlarmDTO alarm) {

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
