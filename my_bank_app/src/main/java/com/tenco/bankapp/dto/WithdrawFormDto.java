package com.tenco.bankapp.dto;

import lombok.Data;

@Data // DTO로 변환하려면, getter setter 있어야 함
public class WithdrawFormDto {
	
	private Long amount; // 출금 금액
	private String wAccountNumber; // 출금 계좌번호
	private String password;
	
}
