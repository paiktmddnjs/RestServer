// src/main/java/com/example/demo/model/Post.java

package com.kh.crud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@Data // Getter, Setter, RequiredArgsConstructor, ToString, EqualsAndHashCode 자동 생성
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 인수로 받는 생성자 자동 생성
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private int score;
    private String title;

    @Column(length = 1000)
    private String content;

    private String author;

    private String date;

    private String image;
    private int likes = 0; // 좋아요 수 기본값

    // 좋아요 수를 증가시키는 메서드 (Setter 대신 비즈니스 로직으로 사용)
    public void incrementLikes() {
        this.likes++;
    }
}