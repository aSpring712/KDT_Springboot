package com.tenco.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bankapp.dto.SignUpFormDto;
import com.tenco.bankapp.handler.exception.CustomRestfulException;
import com.tenco.bankapp.repository.entity.User;
import com.tenco.bankapp.repository.interfaces.UserRepository;

@Service
public class UserService {

	// DAO 객체(db에 접근하는 객체)
	@Autowired // 생성자 의존주입 (의존 주입에는 생성자 의존 주입, 메서드 의존 주입이 있음)
	private UserRepository userRepository;
	
	@Transactional
	public int signUp(SignUpFormDto dto) {
		System.out.println(dto.toString());
		
		User user = User.builder()
				.username(dto.getUsername())
				.password(dto.getPassword())
				.fullname(dto.getFullname())
				.build(); // 반드시 마지막에 build() 메서드 호출해야 객체 반환
//				new User(dto.getUsername(), dto.getPassword(), dto.getFullname()); // 대신 빌더 패턴
		
		
		int resultRowCount = userRepository.insert(user); // mapper framework에서 insert 쿼리문 작동 -> service 돌아와서 return -> UserController가 int return 받음
		if(resultRowCount != 1) {
			throw new CustomRestfulException("회원 가입 실패", 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// service를 왜 따로 만드는가? Transaction : DB에서 일어나는 작업의 단위(commit, rollback)
		// 하나의 메서드에서 쿼리문이 select(중복검사), insert(가입) 처리가 2개 이상 작동 -> Transaction 걸어줌
		
		return resultRowCount;
	}
	
	
}
