package com.tenco.bankapp.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tenco.bankapp.handler.exception.CustomRestfulException;
import com.tenco.bankapp.handler.exception.UnAuthorizedException;

// data를 반환할 때, 예외가 발생하는 핸들러를 만들고자 함

/**
 * 예외 발생 시 데이터를 내려 줄 수 있다.
 */

@RestControllerAdvice // IoC 대상 + AOP(관점 지향형) 기반으로 돌아감
public class MyRestfulExceptionHandler {

	// spring boot에서 예외 발생 시 지켜보고 있다가 @RestControllerAdvice 얘가 처리해 줄 것

	// method
	@ExceptionHandler(Exception.class) // 언제 동작하나 ? Exception.class 를 타면
	public void exception(Exception e) {
		System.out.println("----------------");
		System.out.println(e.getClass().getName());
		System.out.println(e.getMessage());
		System.out.println("----------------");
	}

	// 사용자 정의 예외 클래스 활용
	// method
	// 코드 짜다가 try catch 하는 부분에서 new 때려서 여기를 타도록 하면 됨
	@ExceptionHandler(CustomRestfulException.class)
	public String basicException(CustomRestfulException e) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert( '" + e.getMessage() + "' );");
		sb.append("history.back();");
		sb.append("</script>");

		return sb.toString(); // StringBuffer -> string으로 변환 : toString()
	}
	
	@ExceptionHandler(UnAuthorizedException.class) // 어떨 때 호출하는가? UnAuthorizedException.class
	public String unAuthorizedException(UnAuthorizedException e) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert( '" + e.getMessage() + "' );");
		sb.append("location.href='/user/sign-in';");
		sb.append("</script>");

		return sb.toString(); // StringBuffer -> string으로 변환 : toString()
	}
}