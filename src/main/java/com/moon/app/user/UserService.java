package com.moon.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.moon.app.files.FileManager;

@Service
public class UserService {
	
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

}
