package com.kh.crud.controller;

import com.kh.crud.entity.User;
import com.kh.crud.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // -----------------------------------------
    // 1. 회원가입 (POST /api/auth/register) - RegisterPage.jsx 대응
    // -----------------------------------------
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {

        // 1. 아이디 중복 검사
        if (userRepository.existsById(user.getId())) {
            // 409 Conflict 또는 400 Bad Request 반환
            return ResponseEntity.badRequest().body("이미 존재하는 아이디입니다.");
        }

        // 2. 전화번호 중복 검사 (UserRepository에 정의된 findByPhone 사용)
        if (userRepository.findByPhone(user.getPhone()).isPresent()) {
            return ResponseEntity.badRequest().body("이미 존재하는 전화번호입니다.");
        }

        // 3. 새 사용자 저장
        User savedUser = userRepository.save(user);

        // 저장 성공 시 201 Created와 저장된 사용자 정보 반환 (비밀번호는 제외하는 것이 좋음)
        return ResponseEntity.status(201).body(savedUser);
    }

    // -----------------------------------------
    // 2. 로그인 (POST /api/auth/login) - AuthContext.js 대응
    // -----------------------------------------
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {

        String id = request.getId();
        String pw = request.getPw();

        // 1. 아이디로 사용자 찾기
        return userRepository.findById(id)
                .map(user -> {
                    // 2. 비밀번호 일치 확인 (실제로는 암호화된 비밀번호를 비교해야 함)
                    if (user.getPw().equals(pw)) {
                        // 로그인 성공 시 사용자 정보 반환 (비밀번호 제외한 DTO 사용 권장)
                        return ResponseEntity.ok(user);
                    } else {
                        return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
                    }
                })
                .orElseGet(() -> ResponseEntity.badRequest().body("사용자를 찾을 수 없습니다.")); // 3. 사용자가 없는 경우
    }

    // -----------------------------------------
    // Login 요청을 위한 DTO
    // -----------------------------------------
    @Data
    public static class LoginRequest {
        private String id;
        private String pw;
    }
}