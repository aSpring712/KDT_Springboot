package com.tenco.bankapp.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.tenco.bankapp.dto.response.BoardDto;

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
		
		// 2. 객체 생성
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
	
	// POST 방식과 exchange 메서드 사용
	@GetMapping("/exchange-test")
	public ResponseEntity<?> restTemplateTest2() {
		
		// 자원 등록 요청 --> POST 방식 사용법
		// 1. URI 객체 만들기
		URI uri = UriComponentsBuilder
				.fromUriString("https://jsonplaceholder.typicode.com")
				.path("/posts")
				.encode()
				.build()
				.toUri();
		
		// 2. 객체 생성
		RestTemplate restTemplate = new RestTemplate();
		
		// exchange 사용 방법
		// 1. HttpHeaders 객체를 만들고 Header 메세지 구성
		// 2. body 데이터를 key=value 구조로 만들기
		// 3. HttpEntity 객체를 생성해서 Header와 결합 후 요청
		
		// 헤더 구성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json; charset=UTF-8");
		
		// 바디 구성 -> JSON 형식의 데이터를 만들어주는 아이
		// LinkedMultiValueMap<>()
		// params.add("title", "블로그 포스트 1"); // 어떤 형식으로 데이터를 보낼지는 서버와의 약속
		Map<String, String> params = new HashMap<>();
		params.put("title", "블로그 포스트 1"); // 어떤 형식으로 데이터를 보낼지는 서버와의 약속
		params.put("body", "후미진 어느 언덕에서 도시락 소풍");
		params.put("userId", "1");
		
		// 헤더와 바디 결합
		HttpEntity<Map<String, String>> requestMessage
			= new HttpEntity<>(params, headers);
		//                   body,   header
		
//		// HTTP 요청 처리 									-- 요청  POST 방식        body            응답은 String class로 받기
//		// 파싱 처리 해야 원하는 값 꺼내 쓸 수 있음 -> 지금은 그냥 String임
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, requestMessage, String.class);
//		//																							 현재는 응답을 String 타입으로 받고있는데 dto로 변환할줄 알아야 함
		
		// 파싱
		ResponseEntity<BoardDto> response = restTemplate.exchange(uri, HttpMethod.POST, requestMessage, BoardDto.class);
		BoardDto boardDto = response.getBody();
//		System.out.println(boardDto.getTitle());
		
		// 어떻게 오는지 확인
		// http://localhost:80/exchange-test
		// 다른 서버에서 넘겨 받은 데이터를 DB에 저장하고자 한다면 
		System.out.println("headers " + response.getHeaders());
		System.out.println("TEST : BDTO " + response.getBody());
		
//		return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
		return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
	}
	
}
