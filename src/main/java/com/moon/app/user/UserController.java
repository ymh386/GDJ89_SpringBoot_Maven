package com.moon.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("join")
	public void join()throws Exception{}
	
	@PostMapping("join")
	public String join(UserVO userVO, @RequestParam("avatar") MultipartFile avatar)throws Exception{
		userService.join(userVO, avatar);
		return "redirect:/";
	}

	
	@GetMapping("login")
	public void login()throws Exception{}
	
	@PostMapping("login")
	public String login(UserVO userVO, HttpSession session)throws Exception{
		
		userVO =userService.detail(userVO);
		if(userVO != null) {
			session.setAttribute("user", userVO);
		}
		
		return "redirect:/";
	}
	
	@GetMapping("logout")
	public String login(HttpSession session)throws Exception{
		session.invalidate();
		return "redirect:/";
	}

}
