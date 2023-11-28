package com.tenco.bankapp.repository.entity;

import java.sql.Timestamp;
import java.text.DecimalFormat;

import org.springframework.http.HttpStatus;

import com.tenco.bankapp.handler.exception.CustomRestfulException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account { // 객체 지향 패러다임에 맞춰 자기 기능 구현
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
	
	// 패스워드 체크 기능
	public void checkPassword(String password) {
		if(this.password.equals(password) == false) {
			throw new CustomRestfulException("계좌 비밀번호가 틀렸습니다", HttpStatus.BAD_REQUEST);
		}
	}
	
	// 잔액 여부 확인
	public void checkBalance(Long balance) {
		if(this.balance.longValue() < balance) {
			throw new CustomRestfulException("출금 잔액이 부족합니다", HttpStatus.BAD_REQUEST);
		}
	}
	
	// 계좌 소유자 확인 기능
	public void checkOwner(Integer principalId) {
		if(this.userId != principalId) {
			throw new CustomRestfulException("본인 계좌가 아닙니다", HttpStatus.BAD_REQUEST);
		}
	}
	
	public String formatBalance() {
		// data format class 활용해 천 단위에 쉼표 찍는 기능을 구현하시오
		// 1,000원
		DecimalFormat df = new DecimalFormat("#,###");
		String formatNumber = df.format(balance);
		return formatNumber + "원";
	}
}
