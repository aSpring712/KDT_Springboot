package com.tenco.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bankapp.dto.SignInFormDto;
import com.tenco.bankapp.dto.SignUpFormDto;
import com.tenco.bankapp.handler.exception.CustomRestfulException;
import com.tenco.bankapp.repository.entity.User;
import com.tenco.bankapp.repository.interfaces.UserRepository;

@Service
public class UserService {

	// DAO 객체(db에 접근하는 객체)
	@Autowired // 생성자 의존주입 (의존 주입에는 생성자 의존 주입, 메서드 의존 주입이 있음)
	private UserRepository userRepository;
	
	@Autowired // DI 처리
	private PasswordEncoder passwordEncoder; // 추상화된 interface 개념 -> 실제 구현 클래스는 WebMvcConfig.java에 있음
	
	@Transactional
	public int signUp(SignUpFormDto dto) {
//		System.out.println(dto.toString()); // 에러 잡기 위해서 logging
		
		// 암호화
//		String rawPwd = dto.getPassword();
//		String hashPwd = passwordEncoder.encode(rawPwd);
//		System.out.println("hashPwd : " + hashPwd);
		
		User user = User.builder()
				.username(dto.getUsername())
//				.password(dto.getPassword())
				.password(passwordEncoder.encode(dto.getPassword())) // 코드 수정
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

	public User signIn(SignInFormDto dto) {
		// 비밀번호 암호화하면서 로직 변경
		
//		// interface에 정의해주기
//		User userEntity = userRepository.findByUsernameAndPassword(dto);
		
		// 1. username으로 아이디 존재 여부 확인
		User userEntity = userRepository.findByUsername(dto);
		if(userEntity == null) {
			throw new CustomRestfulException("존재하지 않는 계정입니다", 
					HttpStatus.BAD_REQUEST);
		}
		
		// 2. 객체 상태값의 비번과 암호화 된 비번 일치 여부 확인
		// 기능 추가
		boolean isPwdMatched = passwordEncoder
				.matches(dto.getPassword(), userEntity.getPassword());
		
		if(isPwdMatched == false) {
			throw new CustomRestfulException("비밀번호가 잘못 되었습니다.", 
					HttpStatus.BAD_REQUEST);
		}
		
		return userEntity;
	}
	
	
}
