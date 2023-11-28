package com.tenco.bankapp.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tenco.bankapp.repository.entity.History;

@Mapper
public interface HistoryRepository {
	
	// 거래내역 등록
	public int insert(History history);
	// 계좌 Id로 update
	public int updateById(History history);
	// 삭제
	public int deleteById(Integer id);
	// 전체 조회
	public List<History> findAll();
	public List<History> findByIdAndDynamicType(@Param("type") String type, @Param("accountId") Integer accountId);
	
	// 계좌 거래내역 조회 -> 동적으로 만들 것이므로 주석 처리
//	public List<History> findByAccountId(String id);
	
	// 동적 쿼리 생성
	// 거래내역 type : 입금, 출금, 전체
	// 반드시 두개 이상의 파라미터 사용시 @Param 를 사용해야 한다
}
