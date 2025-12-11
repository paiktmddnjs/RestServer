package com.kh.crud.repository;

import com.kh.crud.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    // 유저가 게시글 좋아요 눌렀는지 조회
    LikeEntity findByUserIdAndBoardId(Long userId, Long boardId);

    // 좋아요 개수
    Long countByBoardId(Long boardId);

    // 좋아요 눌렀는지 확인 (boolean)
    boolean existsByUserIdAndBoardId(Long userId, Long boardId);

    // 좋아요 취소
    void deleteByUserIdAndBoardId(Long userId, Long boardId);
}
