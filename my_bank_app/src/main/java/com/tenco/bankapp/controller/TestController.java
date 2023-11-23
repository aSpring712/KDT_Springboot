package com.tenco.bankapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // IoC(Inversion of Control, 제어의 역전)의 대상, view를 return
@RequestMapping("/temp") // /temp/temp-test, /temp/temp-test2 .. /temp가 반복되므로 RequestMapping으로 대문 만들어주기
public class TestController {
	
	// GET 방식
	// 주소 설계 - http://localhost:80/temp-test
	//			endpoint - 뭘로 받겠다 적어주면 됨
	@GetMapping("/temp-test") // 앞에 / 적어주는 것이 좋음(prefix 마지막에 / 적어주었으므로 생략) 
	// -> 사용자가 web browser나 mobile로 http 요청 시 이 메서드 실행됨 
	public String tempTest() {
		// 화면은 어떻게 찾아가는가? "/WEB-INF/view/temp.jsp"
		// BUT, yml 파일에 prefix, suffix를 설정해 두었기 때문에 "temp"만 써주어도 됨
		
		// prefix: /WEB-INF/view
		return "temp"; // 화면을 return
		// suffix: .jsp
	}
}
