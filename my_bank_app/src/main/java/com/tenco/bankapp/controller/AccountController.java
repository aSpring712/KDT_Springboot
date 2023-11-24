package com.tenco.bankapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bankapp.handler.exception.CustomRestfulException;
import com.tenco.bankapp.repository.entity.User;
import com.tenco.bankapp.utils.Define;

@Controller // View를 제공하는 controller로 설정
@RequestMapping("/account") // '주소'
public class AccountController {

	@Autowired
	private HttpSession session;
	
	// 임시 예외 발생 확인용 http://localhost:80/account/list
//	@GetMapping("/list")
	@GetMapping({"/list", "/"}) // 얘는 /account/ 해도되고, /account/list 해도 되고
	public String list() {
		// 해당 exception을 던짐 -> MyPageExceptionHandler의 handleRuntimeException(CustomPageException e) 얘가 낚아 채서 ModelAndView로 페이지에 던짐
		// throw new CustomPageException("페이지가 없네요~", HttpStatus.NOT_FOUND);
		
		// 인증 검사 : session있는지 확인하고 넘겨주어야 함
		// User type      Object type   --> DownCasting 처리해서 User type으로 맞춰주어야 함
//		User principal = (User)session.getAttribute("principal");
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		
		if(principal == null) {
			throw new CustomRestfulException("인증된 사용자가 아닙니다", 
					HttpStatus.UNAUTHORIZED);
		}
		
		// prefix 마지에 / 붙어 있으므로
		// suffix
		return "account/list"; // '파일 찾는 경로' 여기는 / 안붙임!
	}
	
}
