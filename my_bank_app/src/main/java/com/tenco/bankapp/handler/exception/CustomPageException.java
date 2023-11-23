package com.tenco.bankapp.handler.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomPageException extends RuntimeException { // RuntimeException 상속 받음
	
	private HttpStatus httpStatus;
	
	// 생성자
	public CustomPageException(String message, HttpStatus status) {
		super(message);
		this.httpStatus = status;
	}

}
