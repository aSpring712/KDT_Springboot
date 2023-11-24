package com.tenco.bankapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bankapp.dto.SaveFormDto;
import com.tenco.bankapp.handler.exception.CustomRestfulException;
import com.tenco.bankapp.repository.entity.Account;
import com.tenco.bankapp.repository.interfaces.AccountRepository;

@Service // IoC 대상 + 싱글톤 관리
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;

	
	/**
	 * 계좌 생성 기능
	 * @param dto
	 * @param priIntegerId
	 */
	@Transactional // select로 계좌 중복 여부 확인 후 중복이 아니면 생성해야 하므로 -> Transactional 걸어줌. 그리고 하나의 서비스가 됨
	public void createAccount(SaveFormDto dto, Integer pricipalId) {
		
		// 계좌 중복 여부 확인
		
		
		Account account = Account.builder()
				.number(dto.getNumber())
				.password(dto.getPassword())
				.balance(dto.getBalance())
				.userId(pricipalId)
				.build();
		
		
		int resultRowCount = accountRepository.insert(account);
		if(resultRowCount != 1) {
			throw new CustomRestfulException("계좌 생성 실패", 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 계좌 목록 보기 기능
	public List<Account> readAccountList(Integer userId) {
		List<Account> list = accountRepository.findByUserId(userId);
		return list;
	}
}
