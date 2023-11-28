package com.tenco.bankapp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tenco.bankapp.dto.DepositFormDto;
import com.tenco.bankapp.dto.SaveFormDto;
import com.tenco.bankapp.dto.TransferFormDto;
import com.tenco.bankapp.dto.WithdrawFormDto;
import com.tenco.bankapp.handler.exception.CustomRestfulException;
import com.tenco.bankapp.handler.exception.UnAuthorizedException;
import com.tenco.bankapp.repository.entity.Account;
import com.tenco.bankapp.repository.entity.History;
import com.tenco.bankapp.repository.entity.User;
import com.tenco.bankapp.repository.interfaces.AccountRepository;
import com.tenco.bankapp.service.AccountService;
import com.tenco.bankapp.utils.Define;

@Controller // View를 제공하는 controller로 설정
@RequestMapping("/account") // '주소'
public class AccountController {

	@Autowired
	private HttpSession session;
	
	@Autowired
	private AccountService accountService;
	
	// 임시 예외 발생 확인용 http://localhost:80/account/list
//	@GetMapping("/list")
	@GetMapping({"/list", "/"}) // 얘는 /account/ 해도되고, /account/list 해도 되고
	public String list(Model model) {
		// 해당 exception을 던짐 -> MyPageExceptionHandler의 handleRuntimeException(CustomPageException e) 얘가 낚아 채서 ModelAndView로 페이지에 던짐
		// throw new CustomPageException("페이지가 없네요~", HttpStatus.NOT_FOUND);
		
		// 인증 검사 : session있는지 확인하고 넘겨주어야 함
		// User type      Object type   --> DownCasting 처리해서 User type으로 맞춰주어야 함
//		User principal = (User)session.getAttribute("principal");
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		
		// ! 인증검사 안하고있음!!! -> interceptor에서 가로채서 검사
//		if(principal == null) {
//			// throw new CustomRestfulException("인증된 사용자가 아닙니다",
//			// 로그인하지 않았을 때 예외처리 생성해서 적용
//			throw new UnAuthorizedException("인증된 사용자가 아닙니다", 
//					HttpStatus.UNAUTHORIZED);
//		}
		
		List<Account> accountList = accountService.readAccountList(principal.getId());
		
		System.out.println("accountList : " + accountList.toString());
		accountList.forEach((e) -> {
			System.out.println("===========================");
			System.out.println(e.toString());
		});
		
		if(accountList.isEmpty()) {
			model.addAttribute("accountList", null);
		} else {
			model.addAttribute("accountList", accountList); // modelAndView 타서 데이터 넘길 수 있음
		}
		
		// prefix 마지에 / 붙어 있으므로
		// suffix
		return "account/list"; // '파일 찾는 경로' 여기는 / 안붙임!
	}
	
	@GetMapping("/save")
	public String save() {
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		
		if(principal == null) {
			throw new UnAuthorizedException("로그인 먼저 해주세요", 
					HttpStatus.UNAUTHORIZED);
		}
		
		return "account/save";
	}
	
	@PostMapping("/save")
	public String saveProc(SaveFormDto dto) {
		
		// 1. 인증검사 -> 매번 반복해서 해줘야 함(필터 처리도 있지만 보다 스프링 컨테이너 내에서 인터셉터 처리하기. 스프링 시큐리티를 사용하는게 아니라면)
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		
		if(principal == null) {
			throw new UnAuthorizedException("로그인 먼저 해주세요", 
					HttpStatus.UNAUTHORIZED);
		}
		
		// 2. 유효성 검사
		if(dto.getNumber() == null || dto.getNumber().isEmpty()) {
			throw new CustomRestfulException("계좌번호를 입력하시오", HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("계좌 비밀번호를 입력하시오", HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getBalance() == null || dto.getBalance() <= 0) {
			throw new CustomRestfulException("잘못된 입력입니다", HttpStatus.BAD_REQUEST);
		}
		
		// 인증검사, 유효성 검사를 마쳤다면 생성하는 일은 Service에게 위임
		accountService.createAccount(dto, principal.getId());
		
		return "account/list";
	}
	
	// 출금 페이지 요청
	@GetMapping("/withdraw")
	public String withdraw() {
		
		// 1. 인증검사 -> 매번 반복해서 해줘야 함(필터 처리도 있지만 보다 스프링 컨테이너 내에서 인터셉터 처리하기. 스프링 시큐리티를 사용하는게 아니라면)
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		
		if(principal == null) {
			throw new UnAuthorizedException("로그인 먼저 해주세요", 
					HttpStatus.UNAUTHORIZED);
		}
		
		
		return "account/withdraw";
	}
	
	@PostMapping("/withdraw")
	public String withdrawProc(WithdrawFormDto dto) {
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		
		if(principal == null) {
			throw new UnAuthorizedException("로그인 먼저 해주세요", 
					HttpStatus.UNAUTHORIZED);
		}
		
		if(dto.getAmount() == null) {
			throw new CustomRestfulException("금액을 입력하시오", HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getAmount().longValue() <= 0) {
			throw new CustomRestfulException("출금 금액이 0원 이하일 수 없습니다", HttpStatus.BAD_REQUEST);
		}
	
		if(dto.getWAccountNumber() == null || dto.getWAccountNumber().isEmpty()) {
			throw new CustomRestfulException("계좌 번호를 입력하시오", HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("비밀 번호를 입력하시오", HttpStatus.BAD_REQUEST);
		}
		
		// 서비스 호출
		accountService.updateAccountWithdraw(dto, principal.getId());
		
		return "redirect:/account/list";
	}
	
	// 입금 페이지 요청
	@GetMapping("/deposit")
	public String deposit() {
		// 1. 인증검사 -> 매번 반복해서 해줘야 함(필터 처리도 있지만 보다 스프링 컨테이너 내에서 인터셉터 처리하기. 스프링 시큐리티를 사용하는게 아니라면)
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		
		if(principal == null) {
			throw new UnAuthorizedException("로그인 먼저 해주세요", 
					HttpStatus.UNAUTHORIZED);
		}
		
		return "account/deposit";
	}
	
	// 입금 처리
	@PostMapping("/deposit")
	public String depositProc(DepositFormDto dto) {
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		
		if(principal == null) {
			throw new UnAuthorizedException("로그인 먼저 해주세요", 
					HttpStatus.UNAUTHORIZED);
		}
		
		if(dto.getAmount() == null) {
			throw new CustomRestfulException("금액을 입력해주세요", HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getAmount().longValue() <= 0) {
			throw new CustomRestfulException("입금 금액이 0원 이하일 수 없습니다", HttpStatus.BAD_REQUEST);
		}
	
		if(dto.getDAccountNumber() == null || dto.getDAccountNumber().isEmpty()) {
			throw new CustomRestfulException("계좌 번호를 입력하시오", HttpStatus.BAD_REQUEST);
		}
		
		// 서비스 호출
		accountService.updateAccountDedposit(dto);
		
		return "redirect:/account/list";
	}
	
	// 출금 페이지 요청
	@GetMapping("/transfer")
	public String transfer() {
		// 1. 인증검사 -> 매번 반복해서 해줘야 함(필터 처리도 있지만 보다 스프링 컨테이너 내에서 인터셉터 처리하기. 스프링 시큐리티를 사용하는게 아니라면)
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		
		if(principal == null) {
			throw new UnAuthorizedException("로그인 먼저 해주세요", 
					HttpStatus.UNAUTHORIZED);
		}
		
		return "account/transfer";
	}
	
	// 이체 처리
	@PostMapping("/transfer")
	public String transferProc(TransferFormDto dto) {
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		
		if(principal == null) {
			throw new UnAuthorizedException("로그인 먼저 해주세요", 
					HttpStatus.UNAUTHORIZED);
		}
		
		if(dto.getAmount() == null) {
			throw new CustomRestfulException("이체 금액을 입력하시오", HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getAmount().longValue() <= 0) {
			throw new CustomRestfulException("이체 금액이 0원 이하일 수 없습니다", HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getWAccountNumber() == null || dto.getWAccountNumber().isEmpty()) {
			throw new CustomRestfulException("출금 계좌번호를 입력하시오", HttpStatus.BAD_REQUEST);
		}
		
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("비밀 번호를 입력하시오", HttpStatus.BAD_REQUEST);
		}
	
		if(dto.getDAccountNumber() == null || dto.getDAccountNumber().isEmpty()) {
			throw new CustomRestfulException("이체 계좌번호를 입력하시오", HttpStatus.BAD_REQUEST);
		}
		
		// 서비스 호출
		accountService.updateAccountTransfer(dto, principal.getId());
		
		return "redirect:/account/list";
	}
	
	// 계좌 상세보기 화면 요청 처리 - 데이터를 입력 받는 방법 정리
	// http://localhost/account/detail/1  -> pathVariable : @PathVariable
	// http://localhost/account/detail/1?type=deposit -> Query String : @RequestParam
	// http://localhost/account/detail/1?type=withdraw
	// request param에 기본값 세팅 가능
	@GetMapping("/detail/{accountId}") // 주소에서 넘겨받은 값을 method 안에서 사용해야 함
	public String detail(@PathVariable Integer accountId, 
			@RequestParam(name = "type", defaultValue = "all", required = false) String type, // defaultValue있을 시 반드시 required true or false 값 주어야 함
			Model model) { // jsp에 데이터 내려주기 위해 사용
		
		// 인증검사, 유효성 검사
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizedException("로그인 먼저 해주세요", 
					HttpStatus.UNAUTHORIZED);
		}
		
		// 상세 보기 화면 요청시 --> 데이터를 내려주어야 한다.
		// account 데이터, 정근주체(principalId), 거래내역 정보
		Account account = accountService.findById(accountId); // 하나의 계좌에 대한 정보가 필요
		List<History> historyList = accountService.readHistoryListByAccount(type, accountId);
		
		// JSP에 넘겨주기
		model.addAttribute(Define.PRINCIPAL, principal);
		model.addAttribute("account", account);
		model.addAttribute("historyList", historyList);
		
		return "account/detail";
	}
}
