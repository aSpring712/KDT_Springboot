package com.tenco.bankapp.dto.response;

import lombok.Data;

@Data // for getter, setter
// obejct mapper or message converter
public class BoardDto {
	private String title;
	private String body;
	private String userId;
	private String id;
}
