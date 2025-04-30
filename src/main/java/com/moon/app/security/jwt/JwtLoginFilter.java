package com.moon.app.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter{
	
	
	private JwtTokenManager jwtTokenManager;
	
	private AuthenticationManager authenticationManager;
	
	//login 요청을 처리
	public JwtLoginFilter(AuthenticationManager authenticationManager, JwtTokenManager jwtTokenManager) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenManager=jwtTokenManager;
		this.setFilterProcessesUrl("/users/login");
	}
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		log.info("Login 시도");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UsernamePasswordAuthenticationToken authenticationToken;
		authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		//UserDetails의 loadByUsername호출
		Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
		
		log.info("Result : {} ", authentication);
		
		return authentication;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.info("authResult: {} ", authResult);
		
		//Token 생성하고 Client로 전송
		String token = jwtTokenManager.createToken(authResult);
		log.info("TOKEN : {} ", token);
		
		Cookie cookie = new Cookie("accessToken", token);
		cookie.setMaxAge(120); //쿠키를 어디까지 담을 지 정함
		cookie.setPath("/"); //쿠키 범위 허용
//		cookie.setSecure(true); SSL통신시에만 쿠키를 저장하게 한다
//		cookie.setHttpOnly(true); //자바스크립트에서 쿠키를 변경 할 수 없게 한다
		response.addCookie(cookie);
		
		response.sendRedirect("/");
//		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/commons/jwtLoginResult.jsp");
//		view.forward(request, response);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.info("Fail : {}", failed);
		//로그인이 실패 했을 때 실행
		response.sendRedirect("/user/login");
	}

}
