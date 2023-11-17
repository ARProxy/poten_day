package com.example.repository;

import com.example.entity.Skin;
import com.example.entity.SkinImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkinImgRepository extends JpaRepository<SkinImg, Long> {
    Optional<SkinImg> findById(Long skinImgId);

}
