package com.moon.app.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moon.app.user.UserVO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ChatHandler implements WebSocketHandler{
	
		@Autowired
		private ChatDAO chatDAO;
	
		//socket으로 연결된 전체 유저
		//BroadCast
		private List<WebSocketSession> list = new ArrayList<>();
		
		//socket으로 연결된 전체 유저
		//key : username, value : session
		private Map<String, WebSocketSession> users = new HashMap<>();
		
		//대화방의 대화 내용을 임시 저장(DB에 저장)
		//사용 X
		private Map<Long, List<MessageVO>> messages = new HashMap<>();
		

		@Override
		public void afterConnectionEstablished(WebSocketSession session) throws Exception {
			// TODO Auto-generated method stub
			//Client가 WebSocket 연결시 실행
			
			list.add(session);
			
			users.put(session.getPrincipal().getName(), session);
			
			log.info("{}", session.getPrincipal().getName());
			log.info("{}", (UserVO)(((Authentication)session.getPrincipal()).getPrincipal()));
			//UserVO userVO = (UserVO)session.getPrincipal().;
			
			
		}

		@Override
		public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
			//WebSocket으로 연결된 Client가 메세지를 송신 했을때
			
			ObjectMapper objectMapper = new ObjectMapper();
			MessageVO messageVO = objectMapper.readValue(message.getPayload().toString(), MessageVO.class);
			
			messageVO.setSender(session.getPrincipal().getName());
			
			//1:1 통신시 DB에서 ROOM정보를 조회
			List<MessageVO> rooms = chatDAO.room(messageVO);
			
			messageVO.setRoomNum(rooms.get(0).getRoomNum());
			
			
//			if(!messages.containsKey(messageVO.getRoomNum())) {
//				List<MessageVO> list = new ArrayList<>();
//				list.add(messageVO);
//				messages.put(messageVO.getRoomNum(), list);
//			}else {
//				messages.get(messageVO.getRoomNum()).add(messageVO);
//			}
			
			chatDAO.addChat(messageVO);
			
			try {
				users.get(messageVO.getReceiver()).sendMessage(message);
			}catch (Exception e) {
				e.printStackTrace();
			}
			users.get(messageVO.getSender()).sendMessage(message);
			
//			if(messages.get(messageVO.getRoomNum()).size()>2) {
//				List<MessageVO> copy = messages.get(messageVO.getRoomNum());
//				messages.put(messageVO.getRoomNum(), new ArrayList<>());
//				int result = chatDAO.add(copy);
//			}
			
		}

		@Override
		public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
			// TODO Auto-generated method stub
			//WebSocket오류가 발생 했을 때
			
		}

		@Override
		public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
			// TODO Auto-generated method stub
			// Websocket 연결이 종료 되었을 때
			list.remove(session);
			
		}

		@Override
		public boolean supportsPartialMessages() {
			// TODO Auto-generated method stub
			//용량이 큰 데이터를 나눠서 받을 수 있는지 여부를 결정
			// true이면서 톰켓이 지원을 하면 handleMessage를 여러번 호출
			return false;
		}
		
		

}
