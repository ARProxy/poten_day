package com.example.repository;

import com.example.entity.PostIt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostItRepository extends JpaRepository<PostIt, Long> {
    boolean existsByBoard_User_IdAndPictureIsNotNull(Long userId);
}
