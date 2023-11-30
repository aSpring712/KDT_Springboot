
package com.tenco.bankapp.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoProfile {

    private Long id;
    private String connectedAt;
    private Properties properties;
 
    // 하나의 java 파일에 public 키워드는 1번만 사용 가능 -> 내부 클래스는 괜찮음
    @Data
    public class Properties {
    	private String nickname;
    	private String profileImg;
    	private String thumbImg;
    }
}

// 여기에 하나 더 public class 는 안됨
// public ~~ 