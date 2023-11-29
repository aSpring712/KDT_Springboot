package com.tenco.bankapp.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

// view resorved(return type을 String으로 하면 페이지 반환)를 타게할 것이 아니라 Data를 반환할 것
@RestController
public class RestHomeController {
	
	// @ResponseBody -> data를 반환(View resorve를 타는게 아니라)
	
	// web browser ----> 서버
	// http://localhost:80/todos/1
//	@GetMapping("/todos/{id}") // path variable
//	public String restTemplateTest1(@PathVariable Integer id) {
//		
//		// MIME TYPE : text/html;charset=UTF-8
//		return "안녕 반가워";
//	}
	
	@GetMapping("/todos/{id}") // path variable
	public ResponseEntity<?> restTemplateTest1(@PathVariable Integer id) {
		
		// 다른 서버에 자원 요청(https://jsonplaceholder.typicode.com/todos 이 서버로 데이터 요청 할 것)
		// 1. URI 클래스를 만들어 주어야 한다.
		URI uri = UriComponentsBuilder
				.fromUriString("https://jsonplaceholder.typicode.com") // baseUri만 세팅
				.path("/todos")  // pathVariable 방식으로
				.path("/" + id) // 동적으로 전달받은 id값 사용
				.encode()
				.build()
				.toUri(); // 이렇게 하지않고 new URI 해도 됨
		
		// 2. 메모리에 올리기
		RestTemplate restTemplate = new RestTemplate();
		
		// 다른 서버에 접근해서 자원 요청
		//				 							위에서 get 방식으로 요청하므로
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class); // get 방식으로 entity를 요청하겠다 (uri, 응답 방식)
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody());
		System.out.println(response.getHeaders());
		
		// MIME TYPE : text/html;charset=UTF-8	
		return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
	}
	
}
