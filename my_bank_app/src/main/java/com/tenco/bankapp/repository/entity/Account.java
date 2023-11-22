package com.tenco.bankapp.repository.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	private Integer id;
	private String number;
	private String password;
	private Long balance; // bigint
	private Integer userId; // Integer -> wrapper class
	private Timestamp createdAt;
	
	// 출금 기능
	public void withdraw(Long amount) {
		// 잔액 부족한데 출금하려고 할 때?
		// 방어적 코드 작성 예정
		this.balance -= amount;
	}
	
	// 입금 기능
	public void deposit(Long amount) {
		this.balance += amount;
	}
	
	// TODO - 추후 추가
	// 패스워드 체크 기능
	// 잔액 여부 확인
	// 계좌 소유자 확인 기능
}
