package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PostItForm {

    private Long boardId;
    private String contents;
    private MultipartFile picture;
}
