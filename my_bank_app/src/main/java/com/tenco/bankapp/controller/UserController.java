package com.tenco.bankapp.controller;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.tenco.bankapp.dto.SignInFormDto;
import com.tenco.bankapp.dto.SignUpFormDto;
import com.tenco.bankapp.handler.exception.CustomRestfulException;
import com.tenco.bankapp.repository.entity.User;
import com.tenco.bankapp.service.UserService;
import com.tenco.bankapp.utils.Define;


@Controller // 파일을 return 시킬 것 (View를 제공하는 controller로 설정)
@RequestMapping("/user") // URL 주소를 맵핑
public class UserController {
	
	// DI 처리 (아래 DI(의존 주입) 코드 대신)
	@Autowired
	private UserService userService; // 2. 포함 관계를 위한 선언

//			// DI(의존 주입) -> 아래처럼 하는 대신 위에 @Autowired
//			public UserController(UserService userService) {
//				this.userService = userService;
//			}
	
	// 세션
	@Autowired
	private HttpSession session; // 선언만 했지 new 한적 없으므로 @Autowired해서 가지고 와야 함

	// Domain 설계 할 것
	
	// 회원 가입 페이지 요청
	// 주소 설계  http://localhost:80/user/sign-up
	// method
	@GetMapping("/sign-up") // 자원(페이지) 요청
	public String signUp() {
		return "user/signUp";
	}
	
	// 로그인 페이지 요청
	// 주소 설계  http://localhost:80/user/sign-in
	@GetMapping("/sign-in")
	public String signIn() {
		return "user/signIn";
	}
	
	// DTO - Obejct Mapper가 동작할 수 있도록 DTO 방식으로 설계 or Massage Converter
	// 회원가입 -> 완료 시 로그인 page로 redirect (파일 return이므로 String)
	/**
	 * 회원 가입 처리
	 * @param dto
	 * @return 리다이렉트 로그인 페이지
	 */
	@PostMapping("/sign-up") // 자원의 생성 요청이므로 'Post Mapping'. sign-up이라고 똑같이 이름지을 수 있는 이유 ? RESTful API이기 때문
	public String signUpProc(SignUpFormDto dto) {
		
		// 1. 유효성 검사(회원가입 외에는 인증검사도 해야 함 -> 둘중에 인증검사가 우선이어야 함)
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력하세요", 
					HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password를 입력하세요", 
					HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getFullname() == null || dto.getFullname().isEmpty()) {
			throw new CustomRestfulException("fullname을 입력하세요", 
					HttpStatus.BAD_REQUEST);
		}
		
		// 2. 일 하라고 Service에 던지기(위임 -> userController가 userService를 가지고 있어야 함 -> 포함 관계)

		// 사용자 프로필 이미지 등록 처리
		MultipartFile file = dto.getFile();
		// 등록된 파일이 있다면
		if(file.isEmpty() == false) {
			// 파일 사이즈 체크
			if(file.getSize() > Define.MAX_FILE_SIZE) {
				throw new CustomRestfulException("파일 크기는 20MB 이상 클 수 없어요", 
						HttpStatus.BAD_REQUEST);
			}
		}
		
		// java 입출력 io
		try {
			// 업로드 파일 경로
			String saveDirectory = Define.UPLOAD_DIRECTORY;
			// 폴더가 없다면 오류 발생 -> 미리 폴더 만들어 놓거나
			
			// 폴더 만드는 코드 작성
			File dir = new File(saveDirectory);
			if(dir.exists() == false) {
				dir.mkdir(); // 폴더가 없다면 생성
			}
			
			// 파일 이름 (중복 예방 처리)
			UUID uuid = UUID.randomUUID();
			// 새로운 파일 이름 생성
			String fileName = uuid + "_" + file.getOriginalFilename();
			// 전체 경로 지정 생성
			String uploadPath = 
					Define.UPLOAD_DIRECTORY + File.separator + fileName;
			System.out.println("uploadPath : " + uploadPath);
			File destination = new File(uploadPath);
			
			// 반드시 사용
			file.transferTo(destination); // 실제 생성
			
			// 객체 상태 변경
			// TODO insert 처리 하기 위함 -> 쿼리 수정해야 함
			dto.setOriginFileName(file.getOriginalFilename()); // 사용자가 입력한 파일명
			dto.setUploadFileName(fileName);
			
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
			// 실제 프로젝트 시 sysout 말고 SLF4JF ? 사용하기
		}
		
//		int resultRowCount = userService.signUp(dto);
//		if(resultRowCount != 1) {
//			// 다른 처리
//		}
		
		// 주석 TODO test
//		userService.signUp(dto);
		
		// 여기서부터는 새로운 요청이므로 경로 user부터 다시 적어주어야 함
		return "redirect:/user/sign-in"; // 로그인 페이지로 이동
	}
	
	@PostMapping("/sign-in") // 자원의 요청(GET) 이지만 로그인 보안을 위해 POST
	public String signInProc(SignInFormDto dto) {
		// 1. Controller에 Data가 넘어왔을 때, 유효성 검사
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력하시오", 
					HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password를 입력하시오", 
					HttpStatus.BAD_REQUEST);
		}
		
		// 2. 서비스 호출
		User principal = userService.signIn(dto); // principal 접근 주체
		
		// user가 처음 접근 시 JSESSIONID가 발급되며 그걸 같이 저장
		// 3. 세션 처리
		// 세션 : Web Container(WAS)에 cookie + session으로 세션 처리
//		session.setAttribute("principal", principal); // 세션에 사용자 정보 key value값으로 저장
		// 변수의 효용 - principal은 변경하지 않을 것, 오타 많이 날 것이므로 Define
		session.setAttribute(Define.PRINCIPAL, principal); // 세션에 사용자 정보 key value값으로 저장
		
		System.out.println("principal" + principal);
		
		return "redirect:/account/list";
		
		// return redirect 는 완전히 새로운 요청
		// 그냥 return 경로는 session이라던지 이런 정보를 가지고 감
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/user/sign-in";
	}
}
