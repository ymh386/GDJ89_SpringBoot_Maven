package com.moon.app.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
	
	private JwtTokenManager jwtTokenManager;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenManager jwtTokenManager) {
		super(authenticationManager);
		// TODO Auto-generated constructor stub
		this.jwtTokenManager=jwtTokenManager;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		log.info("토큰 검증");
		
		String token= request.getHeader("Authorization");
		
		if(token == null) {
			token = request.getParameter("t");
		}
		
		if(token != null && token.startsWith("Bearer ")) {
			token = token.substring(token.indexOf(" ")+1); 
			try {
				Claims claims =jwtTokenManager.tokenValidation(token);
				
				Authentication authentication = jwtTokenManager.getAuthenticationByToken(claims.getSubject());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				//accesstoken이 만료 되었을 때
				if(e instanceof ExpiredJwtException) {
					token = request.getHeader("RefreshToken");
					log.info("refrech로 다시 만듬");
					try {
						//1. 검증
						Claims claims = jwtTokenManager.tokenValidation(token);
						//2. token 정보를 추출
						//3. Authentication을 SecurityContextHolder에 넣기
						Authentication authentication = jwtTokenManager.getAuthenticationByToken(claims.getSubject());
						SecurityContextHolder.getContext().setAuthentication(authentication);
						//4. accesstoken을 생성
						String accessToken = jwtTokenManager.createAccessToken(authentication);
						//5. response의 Header에 accesstoken을 넣기
						response.setHeader("AccessToken", accessToken);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
				
				
			}
		}
		
		chain.doFilter(request, response);
	}

}
