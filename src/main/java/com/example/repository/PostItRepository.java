package com.example.repository;

import com.example.entity.PostIt;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PostItRepository extends JpaRepository<PostIt, Long> {
    boolean existsByBoard_User_IdAndPictureIsNotNull(Long userId);

    Optional<PostIt> findTopByOrderByIdDesc();
}
