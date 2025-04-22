package com.moon.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user/*")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("mypage")
	public void mypage(@ModelAttribute UserVO userVO) throws Exception{
		
	}
	
	@PostMapping("mypage")
	public void mypage(@Validated(UpdateGroup.class) UserVO userVO,BindingResult bindingResult) {
		
	}
	
	@GetMapping("join")
	public void join(@ModelAttribute UserVO userVO)throws Exception{}
	
	@PostMapping("join")
	public String join(@Validated(JoinGroup.class) UserVO userVO,BindingResult bindingResult, @RequestParam("avatar") MultipartFile avatar)throws Exception{
		log.info("{}", userVO);
		log.info("Count : {} ", bindingResult.getErrorCount());
		
		if(userService.userErrorCheck(userVO, bindingResult)) {
			return "user/join";
		}
		
//		userService.join(userVO, avatar);
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
