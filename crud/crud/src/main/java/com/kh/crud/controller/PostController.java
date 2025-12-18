// src/main/java/com/kh/crud/controller/PostController.java (통합 최종 버전)

package com.kh.crud.controller;

import com.kh.crud.entity.Post;
import com.kh.crud.service.PostService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// 모든 게시글 관련 API를 여기서 처리합니다.
@CrossOrigin(origins = "*") // React 도메인 명시
@RestController
@RequestMapping("/api/posts") // 기본 경로 통일
public class PostController {

    @Autowired
    private PostService postService;

    // -----------------------------------------
    // 1. 게시글 목록 조회 (GET /api/posts)
    // -----------------------------------------
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.findAllPosts();
    }

    // 2. 게시글 추가 (POST /api/posts)
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.savePost(post);
    }

    // -----------------------------------------
    // 3. 게시글 상세 조회 (GET /api/posts/{id}) - BoardController 기능 통합
    // -----------------------------------------
    @GetMapping("/{postId}")
    public Optional<Post> getPost(@PathVariable Long postId) {
        // ID로 게시글을 찾습니다. Optional 반환으로 유연하게 처리
        return postService.findPostById(postId);
    }

    // -----------------------------------------
    // 4. 좋아요 토글 (POST /api/posts/{postId}/like)
    // -----------------------------------------
    @PostMapping("/{postId}/like")
    public ResponseEntity<PostService.LikeResponse> toggleLike(@PathVariable Long postId, @RequestBody LikeRequest request) {

        // 🌟 LikeResponse response = postService.toggleLike(postId, request.getUserId());
        // 🌟 이전에 발생했던 2개 인수 오류를 해결하는 호출입니다.
        PostService.LikeResponse response = postService.toggleLike(postId, request.getUserId());

        return ResponseEntity.ok(response);
    }

    // 5. 좋아요 카운트 조회 (GET /api/posts/{postId}/likes)
    @GetMapping("/{postId}/likes")
    public int getLikeCount(@PathVariable Long postId) {
        return postService.findPostById(postId)
                .map(Post::getLikes)
                .orElse(0);
    }

    // 6. 게시글 삭제 (DELETE /api/posts/{postId})
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        if (!postService.findPostById(postId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    // 7. 게시글 수정 (PUT /api/posts/{postId})
    @PutMapping("/{postId}")
    public Optional<Post> updatePost(@PathVariable Long postId, @RequestBody Post updatedPost) {

        return postService.findPostById(postId)
                .map(post -> {
                    post.setTitle(updatedPost.getTitle());
                    post.setContent(updatedPost.getContent());
                    post.setAuthor(updatedPost.getAuthor());
                    post.setCategory(updatedPost.getCategory());
                    post.setScore(updatedPost.getScore());
                    post.setDate(updatedPost.getDate());
                    post.setImage(updatedPost.getImage());

                    return postService.savePost(post);
                });
    }

    // -----------------------------------------
    // LikeRequest DTO: userId는 String (User 엔티티 ID 타입과 일치)
    // -----------------------------------------
    @Data
    public static class LikeRequest {
        // 🌟 User 엔티티의 ID가 String 타입이므로, Long이 아닌 String으로 수정해야 합니다.
        private String userId;
    }
}