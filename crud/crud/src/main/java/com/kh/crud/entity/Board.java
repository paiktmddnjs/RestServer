package com.kh.crud.entity;

import jakarta.persistence.*;

@Entity
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;
}
