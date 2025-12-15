// src/main/java/com/example/demo/controller/PostController.java

package com.kh.crud.controller;

import com.kh.crud.controller.dto.response.LikeResponse;
import com.kh.crud.entity.Post;
import com.kh.crud.service.PostService; // 🌟 PostService import
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// React와 같은 다른 도메인(localhost:3000)에서 API 호출을 허용합니다. (CORS 설정)
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService; // 🌟 PostService 주입

    // -----------------------------------------
    // 1. 게시글 목록 조회 (GET /api/posts)
    // -----------------------------------------
    @GetMapping
    public List<Post> getAllPosts() {
        // Service 계층 호출
        return postService.findAllPosts();
    }

    // -----------------------------------------
    // 2. 게시글 추가 (POST /api/posts)
    // -----------------------------------------
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        // Service 계층 호출
        return postService.savePost(post);
    }

    // -----------------------------------------
    // 3-1. 좋아요 토글 (POST /api/posts/{postId}/like)
    // -----------------------------------------
    @PostMapping("/{postId}/like")
    // 반환 타입은 LikeResponse로 변경하는 것이 좋습니다.
    public ResponseEntity<LikeResponse> toggleLike(@PathVariable Long postId, @RequestBody LikeRequest request) {

        // Controller는 요청을 받고 Service에 2개의 인수를 전달합니다.
        LikeResponse response = postService.toggleLike(postId, request.getUserId());

        return ResponseEntity.ok(response);
    }

    // -----------------------------------------
    // 3-2. 좋아요 카운트 조회 (GET /api/posts/{postId}/likes)
    // -----------------------------------------
    @GetMapping("/{postId}/likes")
    public int getLikeCount(@PathVariable Long postId) {
        // Service를 통해 조회하고 좋아요 카운트 반환
        return postService.findPostById(postId)
                .map(Post::getLikes)
                .orElse(0);
    }

    // -----------------------------------------
    // 4. 게시글 삭제 (DELETE /api/posts/{postId})
    // -----------------------------------------
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        // Service를 통해 게시글 존재 여부 확인
        if (!postService.findPostById(postId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        // Service를 통해 삭제
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    // -----------------------------------------
    // 5. 게시글 수정 (PUT /api/posts/{postId})
    // -----------------------------------------
    @PutMapping("/{postId}")
    public Optional<Post> updatePost(@PathVariable Long postId, @RequestBody Post updatedPost) {

        // Service를 통해 기존 게시글을 찾고, 수정 로직을 처리한 후 저장합니다.
        return postService.findPostById(postId)
                .map(post -> {
                    // 필드 업데이트 (DTO를 별도로 만들면 더 깔끔하지만, 여기서는 Entity를 재활용)
                    post.setTitle(updatedPost.getTitle());
                    post.setContent(updatedPost.getContent());
                    post.setAuthor(updatedPost.getAuthor());
                    post.setCategory(updatedPost.getCategory());
                    post.setScore(updatedPost.getScore());
                    post.setDate(updatedPost.getDate());
                    post.setImage(updatedPost.getImage());

                    // Service를 통해 저장 (업데이트)
                    return postService.savePost(post);
                });
    }

    // -----------------------------------------
    // LikeRequest DTO (Data Transfer Object)
    // -----------------------------------------
    @Data
    public static class LikeRequest {
        private Long userId;
    }
}