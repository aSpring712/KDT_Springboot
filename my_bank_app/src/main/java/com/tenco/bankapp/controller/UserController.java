package com.tenco.bankapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 파일을 return 시킬 것
@RequestMapping("/user")
public class UserController {

	// Domain 설계 할 것
	
	// 회원 가입 페이지 요청
	// 주소 설계  http://localhost:80/user/sign-up
	// method
	@GetMapping("/sign-up") // 자원(페이지) 요청
	public String signUp() {
		return "user/signUp";
	}
	
	// 로그인 페이지 요청
	// 주소 설계  http://localhost:80/user/sign-in
	@GetMapping("/sign-in")
	public String signIn() {
		return "user/signIn";
	}
}
