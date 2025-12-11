package com.kh.crud.controller;

import com.kh.crud.entity.Board;
import com.kh.crud.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public Board createPost(@RequestBody Board board) {
        return boardService.save(board);
    }

    @GetMapping
    public List<Board> getPosts() {
        return boardService.findAll();
    }

    @GetMapping("/{id}")
    public Board getPost(@PathVariable Long id) {
        return boardService.findById(id);
    }
}