package com.moon.app.websocket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moon.app.user.UserVO;

@Controller
@RequestMapping("/chat/*")
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	@GetMapping("list")
	public String chat(Model model) throws Exception {
		List<UserVO> list = chatService.getList();
		model.addAttribute("list", list);
		return "chat/list";
	}
	
	@GetMapping("room")
	@ResponseBody
	public List<MessageVO> room(MessageVO messageVO) throws Exception {
		List<MessageVO> list = chatService.room(messageVO);
		
		return list;
	}
	
	@GetMapping("updateReadStatus")
	public String updateReadStatus(MessageVO messageVO) throws Exception {
		chatService.updateReadStatus(messageVO);
		return "redirect:/";
	}
	

}
