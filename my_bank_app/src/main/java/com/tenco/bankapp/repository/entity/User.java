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
	private String originFileName;
	private String uploadFileName;
	
	// 기능 : 유저 이미지 set up
	public String setUpUserImage() {    
		return uploadFileName == null ? 
				// 기본 이미지                     // 내가 지정한 경로
				"https://picsum.photos/id/1/350" : "/images/uploads/" + uploadFileName;
	}
}
