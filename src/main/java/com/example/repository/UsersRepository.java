package com.example.repository;

import com.example.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersEntity,Long> {
//    Optional<UsersEntity> findByUserIdAndPw(String userId, String pw);
    Optional<UsersEntity> findByUserId(String userId);
    Optional<UsersEntity> findById(Long id);
}
