package com.ssg.kms.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSocket
@Controller
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

	private final ChatWebSocketHandler chatWebSocketHandler;
	private final NotifierWebSocketHandler notifierWebSocketHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		
		registry.addHandler(chatWebSocketHandler, "/ws").setAllowedOrigins("*");
		registry.addHandler(notifierWebSocketHandler, "/ws/notifier").setAllowedOrigins("*");
	}

}
