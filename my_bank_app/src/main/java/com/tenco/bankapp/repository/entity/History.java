package com.tenco.bankapp.repository.entity;

import java.sql.Timestamp;

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
	
}
