package com.tenco.bankapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tenco.bankapp.handler.AuthInterceptor;

//@Component // 서버가 뜰 때 메모리에 올라가야 함, IoC에 등록이 되지만, 2개 이상의 Ioc에 등록 처리 시에는 Component보다
@Configuration //을 사용. "스프링 부트 설정 클래스이다" 라는 의미
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired // IoC 처리했기 때문에 메모리에서 DI 해오면 됨
	private AuthInterceptor authIntercepter;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) { // callback으로 매개변수 registry가 넘어옴
		registry.addInterceptor(authIntercepter)
			.addPathPatterns("/account/**") // interceptor가 /acount/의 모든 하위 항목으로 요청들어올 때 작동
			.addPathPatterns("/auth/**"); // 추가 방법
//		WebMvcConfigurer.super.addInterceptors(registry);
	}
	
}
