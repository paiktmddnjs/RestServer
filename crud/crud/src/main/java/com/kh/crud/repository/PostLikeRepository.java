package com.kh.crud.repository;

import com.kh.crud.entity.Post;
import com.kh.crud.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    // 특정 사용자가 특정 게시글에 좋아요를 눌렀는지 찾는 쿼리 메서드
    Optional<PostLike> findByUserIdAndPostId(String userId, Long postId);

    Optional<PostLike> findByUserIdAndPost(String userId, Post post);

    int countByPost(Post post);
}