package com.moon.app.board.notice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.moon.app.board.BoardFileVO;
import com.moon.app.board.BoardVO;
import com.moon.app.home.util.Pager;
import com.moon.app.user.UserVO;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
//@RequestMapping("/notice/*")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	@Value("${menu.board.notice.name}")
	private String name;
	
	@ModelAttribute("kind")
	public String getName() {
		return this.name;
	}

//	@GetMapping("list")
	@GetMapping("/notices")
	public Map<String, Object> getList(Pager pager, Model model) throws Exception {
		pager.setKind("k1");
		List<BoardVO> ar = noticeService.getList(pager);
		model.addAttribute("list", ar);
		model.addAttribute("pager", pager);
		
		Map<String, Object> map = new HashMap<>();
		map.put("ar", ar);
		map.put("pager", pager);
		
		return map;
	}
	
//	@GetMapping("detail")
	@GetMapping("/notices/{page}")
	public BoardVO getDetail(@PathVariable(name = "page") Long page)throws Exception{
		BoardVO boardVO = new BoardVO();
		boardVO.setBoardNum(page);
		
		boardVO = noticeService.getDetail(boardVO);
		
		if(boardVO == null) {
			
		}
		
		return boardVO;
	}
	
//	@PostMapping("/notices/")
//	public String add()throws Exception{
//		return "board/add";
//	}
	
	@PostMapping("/notices")
	public int add(NoticeVO noticeVO, @RequestParam(name = "attaches") MultipartFile[] attaches) throws Exception{
		log.info("NoticeVO : {}", noticeVO);
		for(MultipartFile m : attaches) {
			log.info("fileName : {}", m.getOriginalFilename());
		}
		
		
//		noticeVO.setUserName(userVO.getUsername());
		
		int result = noticeService.add(noticeVO, attaches);
		
		
		
		return result;
	}
	
	@DeleteMapping("/notices/{boardNum}")
	public int delete(@PathVariable(name = "boardNum") Long boardNum) throws Exception {
		BoardVO boardVO = new BoardVO();
		boardVO.setBoardNum(boardNum);
		
		int result = noticeService.delete(boardVO);
		
		return result;
	}
	
	@PatchMapping("/notices")
	public int update(NoticeVO noticeVO) throws Exception {
		int result = noticeService.update(noticeVO);
		
		return result;
	}
	
	@GetMapping("fileDown")
	public String getFileDetail(BoardFileVO boardFileVO, Model model) throws Exception {
		boardFileVO = noticeService.getFileDetail(boardFileVO);
		
		model.addAttribute("fileVO", boardFileVO);
		
		return "fileDownView";
	}
}
