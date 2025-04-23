package com.moon.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.moon.app.files.FileManager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService{
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			UserVO userVO = new UserVO();
			userVO.setUsername(username);
			try {
				userVO = userDAO.detail(userVO);
				log.info("{}", userVO);
			} catch (Exception e) {
				e.printStackTrace();
				userVO = null;
			}
		return userVO;
	}

	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private UserDAO userDAO;
	
	@Value("${menu.user.name}")
	private String kind;
	
	@Value("${app.files.base}")
	private String path;
	
	@Autowired
	private FileManager fileManager;
	
	public boolean userErrorCheck(UserVO userVO, BindingResult bindingResult) throws Exception {
		// return이 true면 검증실패
		// return이 false면 검증통과
		boolean check=false;
		
		check = bindingResult.hasErrors();
		
		//password가 일치하는지 검증
		if(!userVO.getPassword().equals(userVO.getPasswordCheck())) {
			check=true;
			bindingResult.rejectValue("passwordCheck", "userVO.password.equal");
		}
		
		//Id 중복 검사
		UserVO checkVO = userDAO.detail(userVO);
		if(checkVO != null) {
			check=true;
			bindingResult.rejectValue("username", "userVO.username.equal");
		}
		return check;
	}
	
	public int join(UserVO userVO, MultipartFile avatar)throws Exception{
		
		String fileName = fileManager.fileSave(path.concat(kind), avatar);
		userVO.setFileName(fileName);
		userVO.setOriName(avatar.getOriginalFilename());	
		
		userVO.setPassword(encoder.encode(userVO.getPassword()));
		
		return userDAO.join(userVO);
	}
	
	public UserVO detail(UserVO userVO)throws Exception{
		UserVO result = userDAO.detail(userVO);
		if(result != null) {
			if(userVO.getPassword().equals(result.getPassword())) {
				return userVO;
			}
			result = null;
		}
		return result;
	}
	
	public UserVO myPage(UserVO userVO) throws Exception{
		userVO = userDAO.detail(userVO);
		
		return userVO;
	}

}
