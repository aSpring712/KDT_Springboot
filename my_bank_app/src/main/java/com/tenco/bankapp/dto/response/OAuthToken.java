package com.tenco.bankapp.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

// Data Parsing을 위해 getter, setter 필요
@Data
// JSON 형식의 코딩 컨센변의 스네이크 케이스를 자바 카멜 노테이션으로 변환 처리
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OAuthToken {

	// 우리는 카멜로 쓰는데 카카오 응답이 스네이크 케이스로 옴..
//	private String access_token;
//	private String token_type;
//	private String refresh_token;
//	private String expires_in;
//	private String scope;
	
	private String accessToken;
	private String tokenType;
	private String refreshToken;
	private String expiresIn;
	private String scope;
	
}
