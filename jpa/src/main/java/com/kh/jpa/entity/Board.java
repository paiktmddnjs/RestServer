package com.kh.jpa.entity;

import com.kh.jpa.enums.CommonEnums;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(length = 100, nullable = false)
    private String boardTitle;

    @Lob
    @Column(nullable = false)
    private String boardContent;

    @Column(length = 100)
    private String originName;

    @Column(length = 100)
    private String changeName;

    @Builder.Default
    private Integer count = 0;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CommonEnums.Status status = CommonEnums.Status.Y;

    // 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_writer", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "board", orphanRemoval = true, cascade = CascadeType.ALL)
    @Builder.Default
    private List<BoardTag> boardTags = new ArrayList<>();

    /* ===== 연관관계 편의 메서드 ===== */
    public void changeMember(Member member) {
        this.member = member;
        if (!member.getBoards().contains(this)) {
            member.getBoards().add(this);
        }
    }

    public void changeFile(String originName, String changeName) {
        if (originName != null) this.originName = originName;
        if (changeName != null) this.changeName = changeName;
    }

    public void addTag(Tag tag) {
        BoardTag boardTag = BoardTag.builder()
                .tag(tag)
                .build();

        boardTag.changeBoard(this);
        this.boardTags.add(boardTag);
    }

    /* ===== PATCH 수정 메서드 (기존 필드 기준) ===== */
    public void updateTitle(String title) {
        if (title != null) {
            this.boardTitle = title;
        }

    }

    public void updateContent(String content) {

        if (content != null) {
            this.boardContent = content;
        }
    }

    public void updateName(String Name) {

        if(Name != null) {
            this.originName = Name;

        }
    }
}
