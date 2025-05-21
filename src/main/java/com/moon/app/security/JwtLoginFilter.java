package com.moon.app.security;


import java.io.IOException;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter{
	
	private AuthenticationManager authenticationManager;
	private JwtTokenManager jwtTokenManager;
	
	public JwtLoginFilter(AuthenticationManager authenticationManager, JwtTokenManager jwtTokenManager) {
		this.setFilterProcessesUrl("/user/login");
		this.authenticationManager = authenticationManager;
		this.jwtTokenManager = jwtTokenManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = this.authenticationManager.authenticate(token);
		
		log.info("Auth : {}" , authentication);
		
		return authentication;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// Token 생성
		String accessToken = jwtTokenManager.createAccessToken(authResult);
		String refreshToken = jwtTokenManager.createRefreshToken(authResult);
		
		response.setHeader("AccessToken", accessToken);
		response.setHeader("RefreshToken", refreshToken);
		
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");
		
//		response.getWriter().print(failed.getMessage());
		
		log.info("Fail : {}",failed.getMessage() );

		//예외 유형에 따른 값을 변경해서 전송 코드 작성 해야 함!!!!!!!!!!!
		int status=500;
		if(failed instanceof BadCredentialsException) {
			status =522;
		}else if(failed instanceof AccountExpiredException) {
			//사용자 유효기간이 만료
			status=523;
		}else if(failed instanceof LockedException) {
			status=524;
		}else if(failed instanceof CredentialsExpiredException) {
			status=525;
		}else if(failed instanceof DisabledException) {
			status=526;
		}else {
			status=521;
		}
		response.setStatus(status);
		
	}

}
