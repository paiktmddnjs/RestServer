package com.kh.jpa.dto;

import com.kh.jpa.entity.Board;

import lombok.Builder;
import lombok.Getter;


public class UpdateDto {
    private String boardTitle;
    private String boardContent;
    private String originName;


    @Getter
    @Builder
    public static class Response {
        private Long boardId;
        private String boardTitle;
        private String boardContent;
        private String originName;

        public static Response from(Board board) {
            return Response.builder()
                    .boardId(board.getBoardId())
                    .boardTitle(board.getBoardTitle())
                    .boardContent(board.getBoardContent())
                    .originName(board.getOriginName())
                    .build();
        }
    }
}