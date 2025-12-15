package com.kh.crud.service;

// src/main/java/com/example/demo/service/AuthService.java

import com.kh.crud.entity.User;
import com.kh.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User registerNewUser(User user) throws IllegalStateException {

        if (userRepository.existsById(user.getId())) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }

        if (userRepository.findByPhone(user.getPhone()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 전화번호입니다.");
        }

        return userRepository.save(user);
    }

    public Optional<User> authenticate(String id, String pw) {

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();

        if (user.getPw().equals(pw)) {
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    public long getUserCount() {
        return userRepository.count();
    }
}