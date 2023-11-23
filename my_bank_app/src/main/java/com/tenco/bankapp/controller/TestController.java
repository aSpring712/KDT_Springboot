package com.tenco.bankapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // IoC의 대상, view를 return
public class TestController {
	
	// GET 방식
	// 주소 설계 - http://localhost:80/temp-test
	
	@GetMapping("temp-test")
	public String tempTest() {
		return "temp"; 
	}
}
