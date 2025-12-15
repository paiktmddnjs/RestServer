package com.kh.crud.controller.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {

    private String category;
    private int score;
    private String title;
    private String content;
    private String author;
    private String date;
    private String image;
}
