package com.tenco.bankapp.repository.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // Builder 패턴을 쓰려면 이걸 넣어주어야 함
public class User {
	// 생성자가 여러개 - 생성자 오버로딩

	private Integer id;
	private String username;
	private String password;
	private String fullname;
	private Timestamp createdAt;
}
