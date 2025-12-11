package com.kh.crud.service;

import com.kh.crud.controller.dto.response.LikeResponse;
import com.kh.crud.entity.Post;
import com.kh.crud.entity.PostLike;
import com.kh.crud.repository.LikeRepository;
import com.kh.crud.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    /** 좋아요 토글 기능 */
    public LikeResponse toggleLike(Long postId, String userId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글 없음"));

        boolean alreadyLiked = likeRepository.existsByPost_IdAndUserId(postId, userId);

        if (alreadyLiked) {
            likeRepository.deleteByPost_IdAndUserId(postId, userId);
        } else {
            likeRepository.save(new PostLike(post, userId));
        }

        int count = likeRepository.countByPost_Id(postId);

        return new LikeResponse(postId, count, !alreadyLiked);
    }

    /** 좋아요 수 가져오기 */
    public int getLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }
}
