package com.example.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryDto {
    private final Long id;
    private String categoryName;

    public CategoryDto(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
}
