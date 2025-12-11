package com.kh.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TAG")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_ID")
    private Long id;

    @Column(name = "TAG_NAME", length = 30, nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Board> boards = new ArrayList<>();
}
