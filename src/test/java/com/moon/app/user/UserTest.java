package com.moon.app.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class UserTest {
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PasswordEncoder encoder;
	@Test
	void test() throws Exception {
		UserVO userVO = new UserVO();
		userVO.setUsername("manager");
		
		String pw = "manager";
		
		userVO.setPassword(encoder.encode(pw));
		userVO.setName("manager");
		
		userDAO.join(userVO);
		
	}

}
