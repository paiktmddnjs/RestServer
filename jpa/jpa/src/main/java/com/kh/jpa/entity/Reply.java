package com.kh.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "REPLY")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPLY_NO")
    private Long id;

    @Column(name = "REPLY_CONTENT", length = 400, nullable = false)
    private String content;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(length = 1)
    private String status;

    @ManyToOne
    @JoinColumn(name = "REF_BNO")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "REPLY_WRITER")
    private Member writer;

}
