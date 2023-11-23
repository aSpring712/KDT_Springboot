package com.tenco.bankapp.handler.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomRestfulException extends RuntimeException {
	
	// http 응답 코드를 내릴 때, 400번대 / 500번대 등 세팅 할 수 있도록
	private HttpStatus status;
	
	// 생성자
	public  CustomRestfulException(String message, HttpStatus httpStatus) {
		// 부모 클래스 : RuntimeException
		super(message); // 부모 생성자 호출 시 넘겨받았던 message 넣을 수 있도록 함
		this.status = httpStatus;
	}
	
}
