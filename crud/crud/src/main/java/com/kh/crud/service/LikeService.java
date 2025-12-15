package com.kh.crud.service;

import com.kh.crud.entity.Post;
import com.kh.crud.entity.PostLike;
import com.kh.crud.entity.User;
import com.kh.crud.repository.PostLikeRepository;
import com.kh.crud.repository.PostRepository;
import com.kh.crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostLikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public int toggleLike(Long postId, String userId) {

        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        Optional<PostLike> like =
                likeRepository.findByUserAndPost(user, post);

        if (like.isPresent()) {
            likeRepository.delete(like.get());
        } else {
            likeRepository.save(new PostLike(user, post));
        }

        return likeRepository.countByPost(post);
    }
}
