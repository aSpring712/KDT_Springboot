package com.tenco.bankapp.dto;

import lombok.Data;

//Data를 담기 위해 Setter가 필요
@Data
public class SignUpFormDto {
	
	// name 태그에 적은것과 매칭시켜서 적기			
	private String username;
	private String password;
	private String fullname;
	
}
