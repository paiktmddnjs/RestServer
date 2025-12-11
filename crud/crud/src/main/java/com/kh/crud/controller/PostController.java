package com.kh.crud.controller;

import com.kh.crud.controller.dto.request.LikeRequest;
import com.kh.crud.controller.dto.response.LikeResponse;
import com.kh.crud.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    private final PostService postService;

    /** 좋아요 토글 */
    @PostMapping("/{postId}/like")
    public LikeResponse like(@PathVariable Long postId,
                             @RequestBody LikeRequest req) {

        return postService.toggleLike(postId, req.getUserId());
    }

    /** 좋아요 수 조회 */
    @GetMapping("/{postId}/likes")
    public int getLikes(@PathVariable Long postId) {
        return postService.getLikeCount(postId);
    }
}

