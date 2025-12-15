package com.kh.crud.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeResponse {

    private Long postId;   // 게시글 ID
    private boolean liked; // 현재 사용자의 좋아요 상태
    private int count;     // 전체 좋아요 수
}
