package com.tenco.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bankapp.dto.SignUpFormDto;
import com.tenco.bankapp.handler.exception.CustomRestfulException;
import com.tenco.bankapp.service.UserService;

@Controller // 파일을 return 시킬 것
@RequestMapping("/user")
public class UserController {
	
	// DI 처리 (아래 DI(의존 주입) 코드 대신)
			@Autowired
			private UserService userService; // 2. 포함 관계를 위한 선언

//			// DI(의존 주입)
//			public UserController(UserService userService) {
//				this.userService = userService;
//			}

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
	
	// DTO - Obejct Mapper가 동작할 수 있도록 DTO 방식으로 설계 or Massage Converter
	// 회원가입 -> 완료 시 로그인 page로 redirect (파일 return이므로 String)
	/**
	 * 회원 가입 처리
	 * @param dto
	 * @return 리다이렉트 로그인 페이지
	 */
	@PostMapping("/sign-up") // 자원의 생성 요청이므로 'Post Mapping'. sign-up이라고 똑같이 이름지을 수 있는 이유 ? RESTful API이기 때문
	public String signUpProc(SignUpFormDto dto) {
		
		// 1. 유효성 검사(회원가입 외에는 인증검사도 해야 함 -> 둘중에 인증검사가 우선이어야 함)
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력하세요", 
					HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password를 입력하세요", 
					HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getFullname() == null || dto.getFullname().isEmpty()) {
			throw new CustomRestfulException("fullname을 입력하세요", 
					HttpStatus.BAD_REQUEST);
		}
		
		// 2. 일 하라고 Service에 던지기(위임 -> userController가 userService를 가지고 있어야 함 -> 포함 관계)

		int resultRowCount = userService.signUp(dto);
		if(resultRowCount != 1) {
			// 다른 처리
		}
		
		// 여기서부터는 새로운 요청이므로 경로 user부터 다시 적어주어야 함
		return "redirect:/user/sign-in"; // 로그인 페이지로 이동
	}
}
