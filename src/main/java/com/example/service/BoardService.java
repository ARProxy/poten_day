package com.example.service;

import com.example.dto.*;
import com.example.entity.*;
import com.example.repository.BoardRepository;
import com.example.repository.CategoryRepository;
import com.example.repository.PostItRepository;
import com.example.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final CategoryRepository categoryRepository;
    private final BoardRepository boardRepository;
    private final UsersRepository usersRepository;
    private final PostItRepository postItRepository;
    private final Environment env;
    public List<CategoryDto> getCategories() {
        List<Category> categories = categoryRepository.findAll();

        if(categories.isEmpty()) {
            throw new IllegalStateException("카테고리 없음");
        }

        return categories.stream()
                .map(category -> new CategoryDto(category.getId(), category.getCategoryName()))
                .collect(Collectors.toList());
    }
    @Transactional
    public List<BoardDto> saveOrUpdateUserCategories(String userId, List<Long> categoryIds) {
        UsersEntity user = usersRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        List<Board> existingBoards = boardRepository.findAllByUserId(user.getId());
        Map<Long, Board> existingBoardsMap = existingBoards.stream()
                .collect(Collectors.toMap(board -> board.getCategory().getId(), board -> board));

        List<Board> updatedBoards = new ArrayList<>();

        for (Long categoryId : categoryIds) {
            Board board = existingBoardsMap.getOrDefault(categoryId, null);
            if (board == null) {
                // 새로운 Board 객체를 생성합니다.
                board = new Board();
                board.setUser(user);
            }

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
            board.setCategory(category);

            updatedBoards.add(boardRepository.save(board));
        }

        // 사용자가 가진 모든 기존 카테고리 중 새로운 목록에 없는 카테고리를 삭제합니다.
        existingBoards.stream()
                .filter(board -> !categoryIds.contains(board.getCategory().getId()))
                .forEach(boardRepository::delete);

        return updatedBoards.stream()
                .map(board -> new BoardDto(board.getId(), board.getCategory().getId(), user.getId(), new ArrayList<>()))
                .collect(Collectors.toList());
    }
//        List<Board> boards = new ArrayList<>();
//        for (Long categoryId : categoryIds) {
//            Category category = categoryRepository.findById(categoryId)
//                    .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
//
//            Board board = new Board();
//            board.setUser(user);
//            board.setCategory(category);
//            boards.add(boardRepository.save(board)); // 바로 저장
//        }
//
//        // 변환된 BoardDto 리스트 반환
//        return boards.stream()
//                .map(board -> new BoardDto(board.getId(), board.getCategory().getId(), user.getId(), new ArrayList<>()))
//                .collect(Collectors.toList());
//    }

//    @Transactional
//    public List<BoardDto> saveUserCategories(Long userId, List<Long> categoryIds) {
//        UsersEntity user = usersRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
//
//        List<Board> boards = new ArrayList<>();
//        for (Long categoryId : categoryIds) {
//            Category category = categoryRepository.findById(categoryId)
//                    .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
//
//            Board board = new Board();
//            board.setUser(user);
//            board.setCategory(category);
//            boards.add(boardRepository.save(board)); // 바로 저장
//        }
//
//        // 변환된 BoardDto 리스트 반환
//        return boards.stream()
//                .map(board -> new BoardDto(board.getId(), board.getCategory().getId(), user.getId()))
//                .collect(Collectors.toList());
//    }


//    public List<BoardDto> getMyCategories(Long userId) {
//        List<Board> boards = boardRepository.findAllByUserId(userId);
//        return boards.stream()
//                .map(board -> new BoardDto(board.getId(), board.getCategory().getId(), userId))
//                .collect(Collectors.toList());
//    }
public List<BoardDto> getMyCategories(Long userId) {
    List<Board> boards = boardRepository.findAllByUserId(userId);
    return boards.stream()
            .map(board -> new BoardDto(
                    board.getId(),
                    board.getCategory().getId(),
                    userId,
                    convertToSkinDtoList(board.getSkins())))
            .collect(Collectors.toList());
}
    private List<SkinDto> convertToSkinDtoList(List<Skin> skins) {
        return skins.stream()
                .map(skin -> new SkinDto(skin.getId(), skin.getSkinImg(), skin.getBoard().getId()))
                .collect(Collectors.toList());
    }
//    @Transactional
//    public void deleteUserCategories(Long userId, List<Long> categoryIds) {
//        List<Board> boards = boardRepository.findAllByUserIdAndCategoryIdIn(userId, categoryIds);
//        boardRepository.deleteAll(boards);
//    }

    public SkinDto getSkinOfBoard(Long userId) {
        Board board = boardRepository.findFirstByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("No board found for user: " + userId));

        // 보드에 연결된 첫 번째 스킨을 찾습니다.
        Skin skin = board.getSkins().stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No skin found for board: " + board.getId()));

        // SkinDto 객체를 생성하여 반환합니다.
        return new SkinDto(skin.getId(), skin.getSkinImg(), board.getId());
    }
    public PostItDto savePostIt(Long userId, Long boardId, String contents, MultipartFile pictureFile) throws Exception {
        String uploadPath = env.getProperty("itemImgLocation");
        String savedFileName = uploadFile(uploadPath, pictureFile.getOriginalFilename(), pictureFile.getBytes());

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));

        PostIt postIt = new PostIt();
        postIt.setBoard(board);
        postIt.setContents(contents);
        postIt.setPicture(savedFileName);
        postIt = postItRepository.save(postIt);

        return new PostItDto(postIt.getId(), boardId, contents, savedFileName);
    }

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileDate) throws Exception {
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + savedFileName;

        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileDate);
        fos.close();

        return savedFileName;
    }

}
