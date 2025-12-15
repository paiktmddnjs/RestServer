package com.kh.crud.entity;
// src/main/java/com/example/demo/model/User.java

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "site_user") // 'USER'는 예약어일 수 있으므로 'site_user'로 변경
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    // 사용자 ID (React 코드에서 `user_${id}` 키로 사용됨)를 Primary Key로 사용
    // JPA는 기본적으로 Long 타입의 ID를 선호하지만, React 코드에 맞추어 String ID를 사용합니다.
    private String id;

    private String pw; // 비밀번호

    @Column(unique = true) // 전화번호 중복 검사를 위한 Unique 설정
    private String phone; // 전화번호

    // *주의*: 실제 서비스에서는 비밀번호를 평문으로 저장하지 않고 반드시 암호화(Hashing)해야 합니다.
}