package com.moon.app.websocket;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nimbusds.oauth2.sdk.Message;

@SpringBootTest
class ChatDAOTest {

	
	@Autowired
	private ChatDAO chatDAO;
	
	
	@Test
	void testAdd() throws Exception {
		List<MessageVO> list = new ArrayList<>();
		MessageVO messageVO = new MessageVO();
		messageVO.setRoomNum(1L);
		messageVO.setSender("sender");
		messageVO.setReceiver("receiver");
		messageVO.setBody("Chatt");
		list.add(messageVO);
		
		messageVO = new MessageVO();
		messageVO.setRoomNum(1L);
		messageVO.setSender("receiver");
		messageVO.setReceiver("sender");
		messageVO.setBody("Chatt2");
		list.add(messageVO);
		
		int result = chatDAO.add(list);
		
		assertNotEquals(result, 0);
	}

}
