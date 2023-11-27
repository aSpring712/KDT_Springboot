package com.tenco.bankapp.dto;

import lombok.Data;

@Data
public class TransferFormDto {
	private Long amount; // 이체 금액
	private String dAccountNumber; // 입금 받을 계좌번호
	private String wAccountNumber; // 출금할 계좌번호
	private String password; // 출금 계좌 비밀번호
}
