package com.moon.app.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;

public class TestWebSocket {
	
	@Autowired
	private ChatHandler chatHandler;
	
	@Scheduled(cron = "*/10 * * * * *")
	public void test() throws Exception {
		WebSocketMessage<?> message = new TextMessage("공지 알려드립니다"); 
		
		chatHandler.handleMessage(null, message);
	}

}
