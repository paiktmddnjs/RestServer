package com.kh.crud.controller;

import com.kh.crud.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    // 좋아요 토글
    @PostMapping("/boards/{boardId}/like")
    public Long toggleLike(
            @PathVariable Long boardId,
            @RequestBody Map<String, Long> body
    ) {
        Long userId = body.get("userId");
        return likeService.toggleLike(userId, boardId);
    }

    // 좋아요 개수 조회
    @GetMapping("/boards/{boardId}/likes")
    public Long getLikeCount(@PathVariable Long boardId) {
        return likeService.getLikeCount(boardId);
    }
}
