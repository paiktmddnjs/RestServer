package com.kh.crud.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeResponse {
    private Long postId;
    private int likeCount;
    private boolean liked; // 현재 눌린 상태인지
}
