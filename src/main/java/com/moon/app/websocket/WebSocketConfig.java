package com.moon.app.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	
	@Autowired
	private ChatHandler chatHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry
		.addHandler(chatHandler, "/ws/chat")
		.setAllowedOriginPatterns("http://localhost:5173", "http://localhost:5173/ws/chat")
		.withSockJS();  //ws프로토콜을 사용하지 못할 때 http프로토콜로 우회시켜 주는것
		
	}
	
	

}
