package com.kh.crud.repository;

import com.kh.crud.entity.Like;
import com.kh.crud.entity.Post;
import com.kh.crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // 이 유저가 이 게시글에 좋아요 했는지?
    Optional<Like> findByUserAndPost(User user, Post post);

    // 특정 게시글의 좋아요 수
    long countByPost(Post post);
}