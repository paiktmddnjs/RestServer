package com.kh.crud.service;

import com.kh.crud.entity.LikeEntity;
import com.kh.crud.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    // 좋아요 토글
    public Long toggleLike(Long userId, Long boardId) {

        // 이미 좋아요 눌렀는지 확인
        LikeEntity like = likeRepository.findByUserIdAndBoardId(userId, boardId);

        if (like != null) {
            // 누른 상태 → 취소
            likeRepository.delete(like);
        } else {
            // 안 누른 상태 → 추가
            LikeEntity newLike = new LikeEntity();
            newLike.setUserId(userId);
            newLike.setBoardId(boardId);
            likeRepository.save(newLike);
        }

        // 현재 좋아요 개수 반환
        return likeRepository.countByBoardId(boardId);
    }

    // 좋아요 개수만 조회
    public Long getLikeCount(Long boardId) {
        return likeRepository.countByBoardId(boardId);
    }
}
