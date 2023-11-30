package com.tenco.bankapp.controller;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.tenco.bankapp.dto.SignInFormDto;
import com.tenco.bankapp.dto.SignUpFormDto;
import com.tenco.bankapp.dto.response.KakaoProfile;
import com.tenco.bankapp.dto.response.OAuthToken;
import com.tenco.bankapp.handler.exception.CustomRestfulException;
import com.tenco.bankapp.repository.entity.User;
import com.tenco.bankapp.service.UserService;
import com.tenco.bankapp.utils.Define;


@Controller // 파일을 return 시킬 것 (View를 제공하는 controller로 설정)
@RequestMapping("/user") // URL 주소를 맵핑
public class UserController {
	
	// DI 처리 (아래 DI(의존 주입) 코드 대신) -> DI 시에도 final 적어주는게 성능상 좋으나, 생성자를 직접 생성해야 함
	@Autowired
	private UserService userService; // 2. 포함 관계를 위한 선언

//			// DI(의존 주입) -> 아래처럼 하는 대신 위에 @Autowired
//			public UserController(UserService userService) {
//				this.userService = userService;
//			}
	
	// 세션
	@Autowired
	private HttpSession session; // 선언만 했지 new 한적 없으므로 @Autowired해서 가지고 와야 함
	
	// 소셜 회원 비밀번호를 위한 초기값 가지고 옴
	@Value("${tenco.key}") // 메모리에 올라갈 때 yml에 세팅했던 값이 담겨 올라감
	private String tencoKey;

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
			System.out.println(e.getMessage()); // 동기적
			// 실제 프로젝트 시 sysout 말고 slf4j ? 사용하기
			// slf4j -> 비동기적으로 돌아가서 성능적으로 나음
		}
		
//		int resultRowCount = userService.signUp(dto);
//		if(resultRowCount != 1) {
//			// 다른 처리
//		}
		
		// 주석 TODO test
		userService.signUp(dto);
		
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
	
	// kakao callback
	// http://localhost:80/user/kakao-callback?code=
	@GetMapping("/kakao-callback")
//	@ResponseBody // user controller는 rest아니고 그냥 컨트롤러라서 suffix, prefix에 따라 jsp 찾으러 감 -> 그게 아니라 data를 반환하고 싶을 때 @ResponseBody를 쓰면 됨
	public String kakaoCallBack(@RequestParam String code) {
		
		// 액세스 토큰 요청 --> Server to Server
		
		RestTemplate rt1 = new RestTemplate();
		// 헤더 구성
		HttpHeaders headers1 = new HttpHeaders();
		headers1.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		// body 구성
		MultiValueMap<String, String> params1 = new LinkedMultiValueMap<>();
		params1.add("grant_type", "authorization_code");
		params1.add("client_id", "9a0461212c8acc6edf4b165840c3cb7d");
		params1.add("redirect_uri", "http://localhost/user/kakao-callback");
		params1.add("code", code);
		
		// 헤더 + body 결합
		HttpEntity<MultiValueMap<String, String>> requestMsg1
			= new HttpEntity<>(params1, headers1);
		
		// 카카오 서버에 요청 처리
//		ResponseEntity<String> response1 = rt1.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, 
//				requestMsg1, String.class);
		ResponseEntity<OAuthToken> response1 = rt1.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, 
				requestMsg1, OAuthToken.class);
		
		System.out.println("===================================");
		System.out.println(response1.getHeaders());
		System.out.println(response1.getBody());
		
		System.out.println(response1.getBody().getAccessToken());
		System.out.println(response1.getBody().getRefreshToken());
		System.out.println("===================================");
		// ============ 여기까지 access token(정상적인 사용자다) 받기 위함 =============== //
		
		
		// ============ access token을 가지고 사용자 정보(프로필 이미지 등) 받아오기 ============ //
		RestTemplate rt2 = new RestTemplate();
		// 헤더 구성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + response1.getBody().getAccessToken());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// 바디 구성 생략(필수 아님)
		
		// 헤더 바디 결합
		HttpEntity<MultiValueMap<String, String>> requestMsg2 
			= new HttpEntity<>(headers2); 
		
		// 요청
		ResponseEntity<KakaoProfile> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, requestMsg2, KakaoProfile.class);
		
		System.out.println("===================================");
		System.out.println(response2.getBody().getProperties().getNickname());
		System.out.println("===================================");
		
		// ============ 여기까지 카카오 서버에 존재하는 정보 요청 처리 ============ //
		System.out.println("================== 카카오 서버 정보 받기 완료 ==================");
		
		// 1. 회원 가입 여부 확인(이 카카오 계정으로)
		// - 최초 사용자라면 우리 사이트에 회원가입을 (자동) 완료 시켜야 함
		// - 추가 정보 입력 화면 -> 카카오 서버에서 제공하지 않는 추가 정보가 있다면 기능을 만들기 --> DB 저장 처리
		
		KakaoProfile kakaoProfile = response2.getBody();
		// 회원가입 요청
		// 소셜 회원 가입자는 전부 비번이 동일하게 된다.
		SignUpFormDto signUpFormDto = SignUpFormDto
										.builder()
										.username("OAuth_" + kakaoProfile.getId() + "_님")
										.fullname("Kakao")
										.password(tencoKey) // 절대 유출되면 안되는 값
										.file(null)
										.originFileName(null)
										.uploadFileName(null)
										.build();
		
		// 최초 -> 여기 oldUser가 null인 상태
		User oldUser = userService.searchUsername(signUpFormDto.getUsername());
		
		System.out.println("************************");
		System.out.println(oldUser);
		
		if(oldUser == null) {
			// oldUser null이라면 최초 회원 가입 처리를 해주어야 함
			// 회원가입 자동 처리
			userService.signUp(signUpFormDto); // 회원가입 됨 -> 세션에 담아줘야 함
//			oldUser.setUsername(signUpFormDto.getUsername());
//			oldUser.setFullname(signUpFormDto.getFullname());
			oldUser = userService.searchUsername(signUpFormDto.getUsername());
		}
		
		// 만약 소셜 로그인 사용자가 회원가입 처리 완료된 사용자라면
		// 바로 세션 처리 및 로그인 처리
		oldUser.setPassword(null);
		session.setAttribute(Define.PRINCIPAL, oldUser);
		
		return "redirect:/account/list"; // controller이기 때문에 view resolved 타게 할 것
	}
}
