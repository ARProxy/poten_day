package com.example.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BoardDto {
    private Long id;
    private Long categoryId;
    private Long userId;
    private List<SkinDto> skins;

    public BoardDto(Long id, Long categoryId, Long userId, List<SkinDto> skins) {
        this.id = id;
        this.categoryId = categoryId;
        this.userId = userId;
        this.skins = skins;
    }
}
