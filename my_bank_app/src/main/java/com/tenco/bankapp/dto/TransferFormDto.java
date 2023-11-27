package com.tenco.bankapp.dto;

import lombok.Data;

@Data
public class TransferFormDto { // 화면에서 보내주는 값을 담음
	private Long amount; // 이체 금액
	private String dAccountNumber; // 입금 받을 계좌번호
	private String wAccountNumber; // 출금할 계좌번호
	private String password; // 출금 계좌 비밀번호
}
