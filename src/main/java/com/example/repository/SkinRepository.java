package com.example.repository;

import com.example.entity.Skin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkinRepository extends JpaRepository<Skin, Long> {
}