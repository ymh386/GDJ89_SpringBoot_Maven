package com.moon.app.user;

import java.util.Enumeration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@PostMapping("update")
	public String update(@Validated(UpdateGroup.class) UserVO userVO,BindingResult bindingResult) {
		return "redirect:/user/mypage";
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
		
		userService.join(userVO, avatar);
		return "redirect:/";
	}

	
	@GetMapping("login")
	public String login(@AuthenticationPrincipal UserVO userVO)throws Exception{
		if(userVO != null) {
			return "redirect:/";
		}
		
		return "user/login";
	}
	
//	@GetMapping("logout")
//	public String login(HttpSession session)throws Exception{
//		session.invalidate();
//		return "redirect:/";
//	}
	
	@GetMapping("myPage")
	public String myPage(@ModelAttribute UserVO userVO, HttpSession session) throws Exception{
//		Enumeration<String> e = session.getAttributeNames();
//		while(e.hasMoreElements()) {
//		}
//		Object obj=session.getAttribute("SPRING_SECURITY_CONTEXT");
//		SecurityContextImpl impl = (SecurityContextImpl)obj;
//		
//		Authentication authen = impl.getAuthentication();
////		log.info("{}", authen.getPrincipal());
		return "user/myPage";
	}
	
	@GetMapping("update")
	public void update(@ModelAttribute UserVO userVO, HttpSession session) throws Exception{
		Enumeration<String> e = session.getAttributeNames();
		while(e.hasMoreElements()) {
			System.out.println(e.nextElement());
		}
		
		Object obj = session.getAttribute("SPRING_SECURITY_CONTEXT");
		System.out.println(obj.getClass());
		
		SecurityContextImpl impl =(SecurityContextImpl)obj;
		
		Authentication authentication= impl.getAuthentication();
		log.info("authentication: {}", authentication);;
	}

}
