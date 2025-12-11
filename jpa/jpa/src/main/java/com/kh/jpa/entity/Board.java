package com.kh.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BOARD")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_NO")
    private Long id;

    @Column(name = "BOARD_TITLE", length = 100, nullable = false)
    private String title;

    @Column(name = "BOARD_CONTENT", columnDefinition = "CLOB", nullable = false)
    private String content;

    @Column(name = "ORIGIN_NAME", length = 100)
    private String originName;

    @Column(name = "CHANGE_NAME", length = 100)
    private String changeName;

    private Integer count;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(length = 1)
    private String status;

    @ManyToOne
    @JoinColumn(name = "BOARD_WRITER")
    private Member writer;

    // Reply 1:N
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

    // Tag N:N
    @ManyToMany
    @JoinTable(
            name = "BOARD_TAG",
            joinColumns = @JoinColumn(name = "BOARD_NO"),
            inverseJoinColumns = @JoinColumn(name = "TAG_ID")
    )
    private List<Tag> tags = new ArrayList<>();

    // 편의 메서드
    public void addReply(Reply reply) {
        replies.add(reply);
        reply.setBoard(this);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getBoards().add(this);
    }
}
