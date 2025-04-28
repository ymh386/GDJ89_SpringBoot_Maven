package com.moon.app.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.moon.app.user.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class SecurityLogoutHandler implements LogoutHandler {
	@Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	private String adminKey;
	@Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	private String restKey;
	@Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
	private String redirect;
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		log.info("Auth : {}", authentication);
		
		//social로그인일 경우 Logout 요청 진행
		if(authentication instanceof OAuth2AuthenticationToken) {
			UserVO userVO =(UserVO)authentication.getPrincipal();
			
			if(userVO.getSns().toUpperCase().equals("KAKAO")) {
				this.kakaoLogout(userVO);
			}
			
		}
		
	}
	
	private void kakaoLogout(UserVO userVO) {
		log.info("admin {}", adminKey);
		log.info("id {}", userVO.getAttributes().get("id"));
		//Admin key 사용시 Parameter 설정
		Map<String, Object> map = new HashMap<>();
		map.put("target_id_type", "user_id");
		map.put("target_id", userVO.getAttributes().get("id"));
		
		WebClient webClient = WebClient.create();
		Mono<String> res = webClient
//			.get()
//			.uri("https://kauth.kakao.com/oauth/logout?client_id=18ba35566fcc3014ad5fc5488b4f152d&logout_redirect_uri=http://localhost:81/user/logout")
//			.retrieve()
//			.bodyToMono(String.class)
//			;
			.post()//method 형식
			.uri("https://kapi.kakao.com/v1/user/logout")
			.header("Authorization", "Bearer "+userVO.getAccessToken())
//			.header("Authorization", "KakaoAK "+adminKey)
//			.bodyValue(map)
			.retrieve()
			.bodyToMono(String.class)
			;
		log.info("Result : {}", res.block());
	}

	
	
}
