package com.kh.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MEMBER")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class Member {

    @Id
    @Column(name = "USER_ID", length = 30)
    private String userId;

    @Column(name = "USER_PWD", nullable = false, length = 100)
    private String userPwd;

    @Column(name = "USER_NAME", nullable = false, length = 15)
    private String userName;

    @Column(length = 254)
    private String email;

    @Column(length = 1)
    private String gender;

    private Integer age;

    @Column(length = 13)
    private String phone;

    @Column(length = 100)
    private String address;

    @Column(name = "ENROLL_DATE")
    private LocalDateTime enrollDate;

    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;

    @Column(length = 1)
    private String status;

    // 1:1 Profile (mappedBy = profile.member)
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Profile profile;

    // Notice 1:N
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    private List<Notice> notices = new ArrayList<>();

    // Board 1:N
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();

    // Reply 1:N
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

    // 편의 메서드
    public void setProfile(Profile profile) {
        this.profile = profile;
        profile.setMember(this);
    }

    public void addNotice(Notice notice) {
        notices.add(notice);
        notice.setWriter(this);
    }

    public void addBoard(Board board) {
        boards.add(board);
        board.setWriter(this);
    }

    public void addReply(Reply reply) {
        replies.add(reply);
        reply.setWriter(this);
    }
}
