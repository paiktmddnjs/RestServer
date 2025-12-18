package com.kh.jpa.service;

import com.kh.jpa.dto.BoardDto;
import com.kh.jpa.dto.UpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface BoardService {
    Long createBoard(BoardDto.Create createDto) throws IOException;
    BoardDto.Response getBoardDetail(Long boardId);
    Page<BoardDto.Response> getBoardList(Pageable pageable);
    UpdateDto.Response update(Long boardId, UpdateDto.Response updateDto);
}
