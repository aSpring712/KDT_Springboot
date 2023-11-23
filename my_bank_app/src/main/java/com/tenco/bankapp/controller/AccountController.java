package com.tenco.bankapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bankapp.handler.exception.CustomPageException;

@Controller
@RequestMapping("/account")
public class AccountController {

	// 임시 예외 발생 확인용 http://localhost:80/account/list
	@GetMapping("/list")
	public void list() {
		// 해당 exception을 던짐 -> MyPageExceptionHandler의 handleRuntimeException(CustomPageException e) 얘가 낚아 채서 ModelAndView로 페이지에 던짐
		throw new CustomPageException("페이지가 없네요~", HttpStatus.NOT_FOUND);
	}
	
}
