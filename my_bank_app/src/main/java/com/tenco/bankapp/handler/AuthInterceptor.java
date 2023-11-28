package com.tenco.bankapp.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tenco.bankapp.handler.exception.UnAuthorizedException;
import com.tenco.bankapp.repository.entity.User;
import com.tenco.bankapp.utils.Define;

// 만드는 방법
// 1. HandlerInterceptor 구현 <- IoC 대상으로 하고싶다
@Component // IoC 대상 - 싱글톤 관리 (Spring container라는 메모리에 올림 -> 언제 어떻게 동작하라는 것인지 세팅 필요 -> config 패키지에)
public class AuthInterceptor implements HandlerInterceptor { // override해서 쓸 수 있다
	// 내부적으로 새로운 객체를 IoC하지않을 것 -> @Bean을 만들지 않을 것

	// AuthIntercepter 객체가 어떠한 url로 들어오면 동작하라고 할 것
	
	// Controller에 들어오기 전에 동작하는 method
	// Controller로 보내려면 return true하면 되고, return false 시 보내지지 않음(Controller로 들어가지 않음)
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("인터셉터 동작 확인");
		// 세션에 사용자 정보 확인
		// web browser에서 요청이 들어왔을 때, 그 하나의 객체에서 session이 있는가 없는가를 보면 됨
		HttpSession session = request.getSession();
		User principal = (User)session.getAttribute(Define.PRINCIPAL); // User로 down casting
		if(principal == null) {
//			response.sendRedirect("/user/sign-in");
			throw new UnAuthorizedException("로그인 먼저 해주세요", HttpStatus.UNAUTHORIZED);
		}
		
//		return HandlerInterceptor.super.preHandle(request, response, handler);
		return true;
	}
	
	// View가 렌더링 되기 전에 호출 되는 메서드
	// View resove 타서 jsp 찾으러 가기 전에 타는 핸들러
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	// 요청 처리가 완료된 후, 즉, 뷰 렌더링이 완료된 후 호출 도는 메서드
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
