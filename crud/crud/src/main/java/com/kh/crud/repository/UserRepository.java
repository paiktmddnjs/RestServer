package com.kh.crud.repository;

import com.kh.crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    // React 코드의 전화번호 중복 검사에 대응하여 전화번호로 사용자를 찾는 메서드 추가
    Optional<User> findByPhone(String phone);
}