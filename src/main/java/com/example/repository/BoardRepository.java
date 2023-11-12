package com.example.repository;

import com.example.dto.BoardDto;
import com.example.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByUserId(Long userId);
    List<Board> findAllByUserIdAndCategoryIdIn(Long userId, List<Long> categoryIds);

    Optional<Board> findFirstByUserId(Long userId);
}
