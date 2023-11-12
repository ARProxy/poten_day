package com.example.dto;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Getter
public class PostItDto {
    private Long id;
    private Long boardId;
    private String contents;
    private String picture;

    public PostItDto(Long id, Long boardId, String contents, String picture) {
        this.id = id;
        this.boardId = boardId;
        this.contents = contents;
        this.picture = picture;
    }
}
