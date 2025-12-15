package com.kh.crud.controller;

import com.kh.crud.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class LikeController {

    private final PostService postService;

    // 좋아요 토글
    @PostMapping("/{postId}/like")
    public ResponseEntity<PostService.LikeResponse> toggleLike(
            @PathVariable Long postId,
            @RequestParam String userId
    ) {
        PostService.LikeResponse response = postService.toggleLike(postId, userId);
        return ResponseEntity.ok(response);
    }
}
