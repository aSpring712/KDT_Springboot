package com.tenco.bankapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bankapp.dto.DepositFormDto;
import com.tenco.bankapp.dto.SaveFormDto;
import com.tenco.bankapp.dto.WithdrawFormDto;
import com.tenco.bankapp.handler.exception.CustomRestfulException;
import com.tenco.bankapp.repository.entity.Account;
import com.tenco.bankapp.repository.entity.History;
import com.tenco.bankapp.repository.interfaces.AccountRepository;
import com.tenco.bankapp.repository.interfaces.HistoryRepository;

@Service // IoC 대상 + 싱글톤 관리
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private HistoryRepository historyRepository; // 추상(interface)으로 의존 -> SOLID 원칙의 DIP 원칙?
	

	
	/**
	 * 계좌 생성 기능
	 * @param dto
	 * @param pricipalId
	 */
	@Transactional // select로 계좌 중복 여부 확인 후 중복이 아니면 생성해야 하므로 -> Transactional 걸어줌. 그리고 하나의 서비스가 됨
	public void createAccount(SaveFormDto dto, Integer principalId) {
		
		// 계좌 중복 여부 확인
		
		
		Account account = Account.builder()
				.number(dto.getNumber())
				.password(dto.getPassword())
				.balance(dto.getBalance())
				.userId(principalId)
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

	// 출금 기능 로직 고민해보기
	// 1. 계좌 존재 여부 확인 -> select
	// 2. 본인 계좌 여부 확인 -> select
	// 3. 계좌 비번 일치 여부 확인 -> select
	// 4. 잔액 여부 확인
	// 5. 출금 처리 --> update
	// 6. 거래 내역 등록 --> insert
	// 7. 트랜잭션 처리
	@Transactional
	public void updateAccountWithdraw(WithdrawFormDto dto, Integer principalId) {
		 
		Account accountEntity = accountRepository.findByNumber(dto.getWAccountNumber());
		
		// 1. 계좌 존재 여부 확인
		if(accountEntity == null) {
			throw new CustomRestfulException("해당 계좌가 없습니다", HttpStatus.BAD_REQUEST);
		}

		// 2. 본인 계좌 여부 확인
		if(accountEntity.getUserId() != principalId) {
			throw new CustomRestfulException("본인 소유 계좌가 아닙니다", HttpStatus.UNAUTHORIZED);
		}
		
		// 3. 계좌 비번 일치 여부 확인
		if(accountEntity.getPassword().equals(dto.getPassword()) == false) {
			throw new CustomRestfulException("출금 계좌 비밀번호가 틀렸습니다", HttpStatus.BAD_REQUEST);
		}
		
		// 4. 잔액 여부 확인
		if(accountEntity.getBalance() < dto.getAmount()) {
			throw new CustomRestfulException("계좌 잔액이 부족합니다", HttpStatus.BAD_REQUEST);
		}
		
		// 5. 출금 처리(Update) (객체 모델 상태값 변경 처리)
		accountEntity.withdraw(dto.getAmount());
		accountRepository.updateById(accountEntity);
		
		// 6. 거래 내역 등록
		History history = new History();
		history.setAmount(dto.getAmount());
		// 출금 처리이므로 출금 거래 내역에는 사용자가 출금 후에 잔액을 입력합니다. (d_balance에 null값 -> 출금, w_balance null -> 입금, 둘 다 null이 아니면 -> 이체)
		history.setWBalance(accountEntity.getBalance());
		history.setDBalance(null); // null 값
		history.setWAccountId(accountEntity.getId());
		history.setDAccountId(null);
		
		int resultRowCount = historyRepository.insert(history);
		
		if(resultRowCount != 1) {
			throw new CustomRestfulException("정상 처리 되지 않았습니다", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 입금 처리 기능
	// 트랜젝션 처리
	// 1. 계좌 존재 여부 확인
	// 2. 입금 처리 -> update
	// 3. 거래 내역 등록 처리 -> insert
	@Transactional
	public void updateAccountDedposit(DepositFormDto dto) {
		// 1. 계좌 존재 여부 확인
		Account accountEntity = accountRepository.findByNumber(dto.getDAccountNumber());
		if(accountEntity == null) {
			throw new CustomRestfulException("해당 계좌는 존재하지 않습니다", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// 2. 입금 처리(update) - 객체 상태값 변경
		accountEntity.deposit(dto.getAmount());
		accountRepository.updateById(accountEntity);
		
		// 3. 거래 내역 등록(insert)
		History history = new History();
		history.setAmount(dto.getAmount());
		history.setWBalance(null);
		// 입금 처리
		history.setDBalance(accountEntity.getBalance());
		history.setWAccountId(null);
		history.setDAccountId(accountEntity.getId());
		
		int resultRowCount = historyRepository.insert(history);
		
		if(resultRowCount != 1) {
			throw new CustomRestfulException("정상 처리 되지 않았습니다", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
