package com.kh.crud.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게시글 N:1 관계
    @ManyToOne
    @JoinColumn(name = "post_id")
    private  com.kh.crud.entity.Post post;

    private String userId;

    public PostLike( com.kh.crud.entity.Post post, String userId) {
        this.post = post;
        this.userId = userId;
    }

    public PostLike(User user,  com.kh.crud.entity.Post post) {
    }
}
