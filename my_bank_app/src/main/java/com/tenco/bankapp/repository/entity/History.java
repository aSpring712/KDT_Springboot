package com.tenco.bankapp.repository.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.tenco.bankapp.utils.TimeStampUtil;
import com.tenco.bankapp.utils.decimalUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {
	
	private Integer id;
//	private Long amount = 10; // error!! 연산의 기본 단위가 int여서 빨간줄 생기는 것
//	private Long amount = 10L; // l. value(변수) = r. value(Literal), r.value에 L 접미사 필요
	private Long amount;
	private Long wBalance;
	private Long dBalance;
	private Integer wAccountId;
	private Integer dAccountId;
	private Timestamp createdAt;
	
	// 거래내역 정보 추가
	private String sender;
	private String receiver;
	private Long balance;
	
	// 시간
	public String formatCreatedAt() {
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		return simpleDateFormat.format(createdAt);
		return TimeStampUtil.timestampToString(createdAt);
	}
	
	public String formatBalance() {
		// data format class 활용해 천 단위에 쉼표 찍는 기능을 구현하시오
		// 1,000원
//		DecimalFormat df = new DecimalFormat("###,###");
//		String formatNumber = df.format(balance);
//		return formatNumber + "원";
		return decimalUtil.decimalToString(balance);
	}
}
