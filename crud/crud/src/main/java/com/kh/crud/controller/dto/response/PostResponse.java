package com.kh.crud.controller.dto.response;

import com.kh.crud.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private Long id;
    private String category;
    private int score;
    private String title;
    private String content;
    private String author;
    private String date; // LocalDateTime을 String으로 변환하여 저장
    private String image;

    // 🔥 좋아요 개수
    private long likeCount;

    // Post Entity를 PostResponse DTO로 변환하는 정적 팩토리 메서드
    public static PostResponse from(Post post) {
        // Entity에서 DTO로 변환할 때, 필요하다면 데이터 가공 로직을 추가합니다.

        // 1. 날짜 포맷팅: LocalDateTime -> String
        String formattedDate = post.getCreatedDate() != null ?
                post.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) :
                "N/A";

        // 2. 좋아요 개수 계산 (만약 Post Entity에 실제 like 개수를 조회하는 메서드가 있다면 사용)
        // 여기서는 Post Entity에 'likeCount' 필드가 있다고 가정하고 매핑합니다.
        // 만약 Entity에 컬렉션으로만 있다면 Service 계층에서 계산해서 DTO를 생성해야 합니다.
        long count = post.getLikeCount(); // Post Entity에 likeCount 필드가 있다고 가정

        return new PostResponse(
                post.getId(),
                post.getCategory(),
                post.getScore(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                formattedDate, // 포맷팅된 날짜 사용
                post.getImage(),
                count // 계산된 좋아요 개수
        );
    }
}