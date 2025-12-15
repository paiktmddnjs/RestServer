package com.kh.crud.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

// Lombok 어노테이션을 사용하여 Getter와 생성자를 자동으로 생성
@Getter
@NoArgsConstructor(force = true) // 기본 생성자
@RequiredArgsConstructor // final 필드를 인수로 갖는 생성자 (선택적)
public class LoginRequest {

    // 사용자가 입력한 ID (아이디)
    private final String id;

    // 사용자가 입력한 Password (비밀번호)
    private final String password;

    // 참고: Spring Boot의 @RequestBody로 JSON을 받을 때,
    // 필드 이름과 JSON 키가 일치해야 하며, 기본 생성자 또는 모든 필드를 받는 생성자가 필요합니다.

    // 만약 Lombok을 사용하지 않는다면 아래와 같이 작성합니다.
    /*
    private String id;
    private String password;

    public LoginRequest() {}

    public LoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }

    // Getter
    public String getId() { return id; }
    public String getPassword() { return password; }
    */
}