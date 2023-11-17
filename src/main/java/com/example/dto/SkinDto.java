package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class SkinDto {

    private Long id;
    private Long skinImg_id;
    private Long boardId;

    public SkinDto(Long id, Long skinImg_id, Long boardId) {
        this.id = id;
        this.skinImg_id = skinImg_id;
        this.boardId = boardId;
    }
}
