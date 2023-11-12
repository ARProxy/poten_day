package com.example.controller;

import com.example.dto.*;
import com.example.entity.Skin;
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
//    @PostMapping("/saveMyCategories")
//    public ResponseEntity<List<BoardDto>> saveMyCategories(
//            @RequestHeader("Authorization") String token,
//            @RequestBody CategoryIdRequest categoryIds) {
//        String actualToken = token.substring(7); // 이 부분은 "Bearer " 접두사를 포함하지 않은 토큰을 가정하고 있습니다.
//        Long userId = tokenUtils.getUserIdFromToken(actualToken);
//        List<BoardDto> updatedBoards = boardService.saveOrUpdateUserCategories(userId, categoryIds.getId());
//        return ResponseEntity.ok().build();
//    }
    @PostMapping("/saveMyCategories")
    public ResponseEntity<?> saveMyCategories(
            @RequestHeader("Authorization") String token,
            @RequestBody CategoryIdRequest categoryIds) {
        System.out.println(token);
        String actualToken = token.substring(7); // "Bearer " 접두사를 제거합니다.
        String userId = tokenUtils.getUserIdFromToken(actualToken); // Long 변환 대신 String 그대로 사용
        boardService.saveOrUpdateUserCategories(userId, categoryIds.getId());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/myCategories")
    public ResponseEntity<List<BoardDto>> getMyCategories(@RequestHeader("Authorization") String token) {
        String actualToken = token.substring(7);
        Long userId = Long.parseLong(tokenUtils.getUserIdFromToken(actualToken)); // String을 Long으로 변환
        List<BoardDto> myBoards = boardService.getMyCategories(userId);
        return ResponseEntity.ok(myBoards);
    }
    @PostMapping("/savePostIt")
    public ResponseEntity<PostItDto> savePostIt(
            @RequestHeader("Authorization") String token,
            @RequestParam("boardId") Long boardId,
            @RequestParam("contents") String contents,
            @RequestParam("picture") MultipartFile pictureFile) throws Exception {

        String actualToken = token.substring(7);
        Long userId = Long.parseLong(tokenUtils.getUserIdFromToken(actualToken)); // String을 Long으로 변환

        PostItDto postItDto = boardService.savePostIt(userId, boardId, contents, pictureFile);
        return ResponseEntity.ok(postItDto);
    }
//    @GetMapping("/myCategories")
//    public ResponseEntity<List<BoardDto>> getMyCategories(@RequestHeader("Authorization") String token) {
//        String actualToken = token.substring(7);
//        Long userId = tokenUtils.getUserIdFromToken(actualToken);
//        List<BoardDto> myBoards = boardService.getMyCategories(userId);
//        return ResponseEntity.ok(myBoards);
//    }

//    @DeleteMapping("/deleteCategories")
//    public ResponseEntity<?> deleteMyCategories(@RequestHeader("Authorization") String token, @RequestBody List<Long> categoryIds) {
//        String actualToken = token.substring(7);
//        Long userId = tokenUtils.getUserIdFromToken(actualToken);
//        boardService.deleteUserCategories(userId, categoryIds);
//        return ResponseEntity.ok().build();
//    }
//    @GetMapping("/skin")
//    public ResponseEntity<SkinDto> getSkinOfBoard(@RequestHeader("Authorization") String token) {
//        String actualToken = token.substring(7); // "Bearer " 접두사를 제거합니다.
//        Long userId = tokenUtils.getUserIdFromToken(actualToken);
//
//        // 스킨 정보를 가져옵니다.
//        SkinDto skinDto = boardService.getSkinOfBoard(userId);
//        if (skinDto == null) {
//            return ResponseEntity.ok(null);
//        } else {
//            return ResponseEntity.ok(skinDto);
//        }
//    }
//    @PostMapping("/savePostIt")
//    public ResponseEntity<PostItDto> savePostIt(
//            @RequestHeader("Authorization") String token,
//            @RequestParam("boardId") Long boardId,
//            @RequestParam("contents") String contents,
//            @RequestParam("picture") MultipartFile pictureFile) throws Exception {
//
//        String actualToken = token.substring(7);
//        Long userId = tokenUtils.getUserIdFromToken(actualToken);
//
//        PostItDto postItDto = boardService.savePostIt(userId, boardId, contents, pictureFile);
//        return ResponseEntity.ok(postItDto);
//    }
}
