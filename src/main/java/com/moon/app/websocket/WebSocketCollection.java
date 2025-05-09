package com.moon.app.websocket;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.reactive.socket.WebSocketSession;

public class WebSocketCollection {
	
	public static final Map<String, WebSocketSession> MAP;
	
	static {
		MAP = new HashMap<>();
	}

}
