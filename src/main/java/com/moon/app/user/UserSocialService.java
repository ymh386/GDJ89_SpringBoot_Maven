package com.moon.app.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserSocialService extends DefaultOAuth2UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// TODO Auto-generated method stub
		
		//ID 로 DB에서 조회
		//데이터가 없다면 DB에 INSERT
		//아니면 조회한데이터로 작성
		
		log.info("Token : {} ", userRequest.getAccessToken());
		ClientRegistration registration = userRequest.getClientRegistration();
		log.info("ID : {} ", registration.getRegistrationId());
		String sns = registration.getRegistrationId();
		
		if(sns.toUpperCase().equals("KAKAO")) {
			try {
				return this.useKakao(userRequest);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	private OAuth2User useKakao(OAuth2UserRequest userRequest) throws Exception {
		OAuth2User user = super.loadUser(userRequest);
		log.info("user : {} ", user);
		log.info("ID : {} ", user.getName());
		log.info("Attr : {} ", user.getAttributes());
		log.info("Auth : {} ", user.getAuthorities());
		
		log.info("prop : {} ", user.getAttributes().get("properties").getClass()); 
		
		Map<String, Object> attr = (Map<String, Object>)user.getAttributes().get("properties");
		
		UserVO userVO = new UserVO();
		
		
		userVO.setUsername(user.getName());
		userVO.setName(attr.get("nickname").toString());
		userVO.setFileName(attr.get("thumbnail_image").toString());
		userVO.setSns(userRequest.getClientRegistration().getRegistrationId());
		
		UserVO userVO2 = userDAO.detail(userVO);
		
		if(userVO2 == null) {
			userDAO.join(userVO);
		}
		
		userVO.setUsername(attr.get("nickname").toString());
		userVO.setAttributes(user.getAttributes());
		userVO.setAccessToken(userRequest.getAccessToken().toString());
		
		List<RoleVO> list = new ArrayList<>();
		RoleVO roleVO = new RoleVO();
		roleVO.setRoleName("ROLE_MEMBER");
		list.add(roleVO);
		
		userVO.setList(list);
		
		return userVO;
	}

}
