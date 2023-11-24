package com.tenco.bankapp.handler.exception;

import org.springframework.http.HttpStatus;

// 사용자 정의 예외 처리
public class UnAuthorizedException extends RuntimeException {

	private HttpStatus status;
	
	// 생성자
	public UnAuthorizedException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}
	
}
