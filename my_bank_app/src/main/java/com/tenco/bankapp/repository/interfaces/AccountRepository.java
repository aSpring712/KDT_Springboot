package com.tenco.bankapp.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tenco.bankapp.repository.entity.Account;

@Mapper
public interface AccountRepository {
	
	public int insert(Account account); // 입금
	public int updateById(Account account); // 계좌 정보 수정
	public int deleteById(Integer id); // 계좌 정보 삭제
	public List<Account> findAll(); // 계좌 전체 조회
	public Account findById(Integer id); // 계좌 id로 하나의 계좌만 조회
	
//	// 사용자의 유저 정보로 계좌 리스트 출력하는 기능
	public List<Account> findByUserId(Integer principalId); // 접근 주체
	public Account findByNumber(String number); // 계좌번호로 조회
	
	
}
