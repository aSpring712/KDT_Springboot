package com.tenco.bankapp.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

//Data를 담기 위해 Setter가 필요
@Data
public class SignUpFormDto {
	
	// name 태그에 적은것과 매칭시켜서 적기			
	private String username;
	private String password;
	private String fullname;
	
	// 단일 file data 받기 -> MultipartFile이 binary 속성의 데이터를 받아줌
	private MultipartFile file; // name 속성 "file"과 일치 시켜야 함
//	파일 여러개 업로드
//	private MultipartFile[] file; // name 속성 "file"과 일치 시켜야 함
	private String originFileName; // 파일 이름 원본
	private String uploadFileName; // 파일 업로드용 이름
	
}
