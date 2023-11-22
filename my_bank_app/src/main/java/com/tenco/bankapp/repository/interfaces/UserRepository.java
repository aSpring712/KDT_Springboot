package com.tenco.bankapp.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tenco.bankapp.repository.entity.User;

@Mapper
public interface UserRepository {

	// 사용자 등록 -> sql문에 mybatis 써서 구문 작성
	public int insert(User user); // mysql에서 insert 구문 날리면 결과 집합 -> result가 int로 떨어짐(row 값) 
	// 사용자 수정
	public int updateById(User user);
	// 사용자 삭제 -> 실패가 거의 없어서 void로 해도 되긴 함
	public int deleteById(Integer id);
	// 사용자 한 명 조회
	public User findById(Integer id);
	// 사용자 전체 조회
	public List<User> findAll();
}
