package com.kh.crud.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id") // 실제 DB 컬럼 이름
    private User user; // <-- 리포지토리 메서드 이름(

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}