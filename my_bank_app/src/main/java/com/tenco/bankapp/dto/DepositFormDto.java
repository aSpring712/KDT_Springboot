package com.tenco.bankapp.dto;

import lombok.Data;

@Data
public class DepositFormDto {
	
	private Long amount; // 입금 금액
	private String dAccountNumber; // 입금 계좌번호
	
}
