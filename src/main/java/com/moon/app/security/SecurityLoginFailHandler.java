package com.moon.app.security;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SecurityLoginFailHandler implements AuthenticationFailureHandler {
	
	@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			log.info("Fail : {}", exception);
			String message="";
			if(exception instanceof BadCredentialsException) {
				message = "비밀번호가 일치하지 않습니다.";
			}else if(exception instanceof AccountExpiredException) {
				//사용자 유효기간이 만료
				message = "사용자 유효기간이 만료되었습니다.";
			}else if(exception instanceof LockedException) {
				//계정 잠김
				message = "계정이 잠겨있습니다.";
			}else if(exception instanceof CredentialsExpiredException) {
				//비번 유효기간 종료
				message = "비밀번호가 만료되었습니다.";
			}else if(exception instanceof DisabledException) {
				//계정 사용 불가
				message = "계정 사용 불가";
			}else {
				//없는 사용자
				message = "없는 계정입니다.";
			}
			
			message = "user.login.password";
			message = URLEncoder.encode(message, "UTF-8");
			
			response.sendRedirect("/user/login?message=".concat(message));
//			request.setAttribute("code", message);
//			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/user/login.jsp");
//			view.forward(request, response);
			
		}

}
