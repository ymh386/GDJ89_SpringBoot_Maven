package com.moon.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
