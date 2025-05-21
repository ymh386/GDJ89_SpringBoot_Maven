package com.moon.app.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;

@Component
public class SendMessagTest {
	
	@Autowired
	private ChatHandler chatHandler;
	
//	@Scheduled(fixedDelay = 3000)
	public void send() throws Exception {
		WebSocketMessage<String> m = new TextMessage("Hello");
		chatHandler.handleMessage(null, m);
	}

}
