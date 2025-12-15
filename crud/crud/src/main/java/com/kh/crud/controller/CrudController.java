package com.kh.crud.controller;

import com.kh.crud.entity.Post;
import com.kh.crud.service.LikeService;
import com.kh.crud.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CrudController {

    private final PostRepository postRepository;
    private final LikeService likeService;

    // 게시글 전체 조회
    @GetMapping("/posts")
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    // 게시글 추가
    @PostMapping("/posts")
    public Post addPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    // 게시글 좋아요 토글
    @PostMapping("/posts/{postId}/like")
    public void toggleLike(
            @PathVariable Long postId,
            @RequestBody Map<String, Long> body
    ) {
        Long userId = body.get("userId");
        likeService.toggleLike(userId, postId);
    }
}
