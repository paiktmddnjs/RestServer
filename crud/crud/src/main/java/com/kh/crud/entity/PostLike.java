package com.kh.crud.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게시글 N:1 관계
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private String userId;

    public PostLike(Post post, String userId) {
        this.post = post;
        this.userId = userId;
    }
}
