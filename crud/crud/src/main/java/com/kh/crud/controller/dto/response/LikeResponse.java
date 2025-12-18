// src/main/java/com/kh/crud/controller/dto/response/LikeResponse.java

package com.kh.crud.controller.dto.response;

import lombok.Data;

@Data
public class LikeResponse {
    private int totalLikes;
    private boolean liked;
}