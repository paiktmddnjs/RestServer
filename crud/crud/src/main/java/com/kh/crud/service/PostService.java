package com.kh.crud.service;
// src/main/java/com/example/demo/service/PostService.java


import com.kh.crud.entity.Post;
import com.kh.crud.repository.PostLikeRepository;
import com.kh.crud.repository.PostRepository;
import com.kh.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import com.kh.crud.entity.PostLike;
import com.kh.crud.entity.User;

import lombok.Data;



@Service
public class PostService {

    // -----------------------------------------
    // 의존성 주입 (Repository)
    // -----------------------------------------
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    // -----------------------------------------
    // LikeResponse DTO: 좋아요 결과 상태를 반환하기 위한 클래스
    // -----------------------------------------
    @Data
    public static class LikeResponse {
        private int totalLikes;
        private boolean liked; // 현재 상태 (좋아요가 눌러져 있는지)
    }
    // -----------------------------------------


    // 1. 전체 게시글 조회
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    // 2. 게시글 저장 (추가/수정 시 사용)
    @Transactional
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    // 3. ID로 게시글 조회
    public Optional<Post> findPostById(Long id) {
        return postRepository.findById(id);
    }

    // 4. 게시글 삭제
    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    // 5. 좋아요 토글 (비즈니스 로직: 중복 방지 및 취소 처리)
    @Transactional
    public LikeResponse toggleLike(Long postId, String userId) { // 🌟 2개 인수로 최종 확정

        // 1. 엔티티 존재 확인 (ID가 없으면 런타임 예외 발생)
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + postId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId));

        // 2. 기존 좋아요 기록 찾기
        Optional<PostLike> existingLike = postLikeRepository.findByUserIdAndPostId(userId, postId);

        int change = 0; // 좋아요 수 변경분

        if (existingLike.isPresent()) {
            // 3-A. 좋아요 취소 (DELETE)
            postLikeRepository.delete(existingLike.get());
            change = -1;

        } else {
            // 3-B. 좋아요 등록 (INSERT)
            // PostLike(User, Post) 생성자 호출. PostLike.java에 정의 필요.
            PostLike newLike = new PostLike(user, post);
            postLikeRepository.save(newLike);
            change = 1;
        }

        // 4. Post 엔티티의 좋아요 수 업데이트 및 저장
        post.setLikes(post.getLikes() + change);
        postRepository.save(post);

        // 5. 결과 DTO 반환
        LikeResponse response = new LikeResponse();
        response.setTotalLikes(post.getLikes());
        response.setLiked(change > 0);

        return response;
    }
}