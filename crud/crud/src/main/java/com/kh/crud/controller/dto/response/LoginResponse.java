package com.kh.crud.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Lombok 어노테이션을 사용하여 Getter와 모든 필드를 인수로 갖는 생성자를 자동으로 생성
@Getter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 인수로 갖는 생성자
public class LoginResponse {

    // 인증 성공 시 발급되는 JWT (JSON Web Token)
    private String token;

    // 로그인에 성공한 사용자 ID
    private String userId;

    // 실제 서비스에서는 보통 토큰 외에
    // 사용자 이름, 역할(Role) 등 추가적인 정보를 포함합니다.

    // 만약 Lombok을 사용하지 않는다면 아래와 같이 작성합니다.
    /*
    private String token;
    private String userId;

    public LoginResponse() {}

    public LoginResponse(String token, String userId) {
        this.token = token;
        this.userId = userId;
    }

    // Getter
    public String getToken() { return token; }
    public String getUserId() { return userId; }
    */
}