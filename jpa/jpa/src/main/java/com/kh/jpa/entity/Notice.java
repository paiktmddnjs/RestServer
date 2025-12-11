package com.kh.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "NOTICE")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTICE_NO")
    private Long id;

    @Column(name = "NOTICE_TITLE", length = 30, nullable = false)
    private String title;

    @Column(name = "NOTICE_CONTENT", length = 200, nullable = false)
    private String content;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "NOTICE_WRITER")
    private Member writer;

}
