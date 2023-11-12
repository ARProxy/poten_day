package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class SkinDto {

    private Long id;
    private String skinImg;
    private Long boardId;

    public SkinDto(Long id, String skinImg, Long boardId) {
        this.id = id;
        this.skinImg = skinImg;
        this.boardId = boardId;
    }
}
