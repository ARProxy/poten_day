package com.example.controller;

import com.example.dto.*;
import com.example.entity.Skin;
import com.example.entity.SkinImg;
import com.example.jwt.TokenUtils;
import com.example.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/board")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class BoardController {

    private final BoardService boardService;
    private final TokenUtils tokenUtils;

    @GetMapping("/category")
    public ResponseEntity<List<CategoryDto>> getCategories(@RequestHeader("Authorization") String token) {
        List<CategoryDto> categories = boardService.getCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/saveMyCategories")
    public ResponseEntity<?> saveMyCategories(
            @RequestHeader("Authorization") String token,
            @RequestBody CategoryIdRequest categoryIds) {
        System.out.println(token);
        String actualToken = token.substring(7);
        String userId = tokenUtils.getUserIdFromToken(actualToken);
        boardService.saveOrUpdateUserCategories(userId, categoryIds.getId());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/myCategories")
    public ResponseEntity<List<CategoryDto>> getMyCategories(@RequestHeader("Authorization") String token) {
        String actualToken = token.substring(7);
        String userId = tokenUtils.getUserIdFromToken(actualToken);
        List<CategoryDto> myCategory = boardService.getMyCategories(userId);
        return ResponseEntity.ok(myCategory);
    }
    @GetMapping("/selectSkin")
    public ResponseEntity<?> saveMySkin(@RequestHeader("Authorization") String token, @RequestParam Long skinImgId) {
        String actualToken = token.substring(7);
        String userId = tokenUtils.getUserIdFromToken(actualToken);
        boardService.saveMySkin(userId, skinImgId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/savePostIt")
    public ResponseEntity<PostItDto> savePostIt(@RequestHeader("Authorization") String token, @ModelAttribute PostItForm postItForm) throws Exception {
        String actualToken = token.substring(7);
        String userId = tokenUtils.getUserIdFromToken(actualToken);
        PostItDto postItDto = boardService.savePostIt(userId, postItForm);
        return ResponseEntity.ok(postItDto);
    }
}
