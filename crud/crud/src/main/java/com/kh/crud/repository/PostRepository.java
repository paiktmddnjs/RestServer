

package com.kh.crud.repository;

// src/main/java/com/example/demo/repository/PostRepository.java

import com.kh.crud.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // Post 엔티티와 기본 키 타입(Long)을 지정
}