package com.example.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "users")
@Entity
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id") // 데이터베이스의 컬럼명과 일치하도록 변경
    private String userId;

    @Column(name = "pw") // 데이터베이스의 컬럼명과 일치하도록 변경
    private String userPw;

    @Column(name = "nickname")
    private String nickName;

    @Builder
    public UsersEntity(String userId, String userPw, String nickName) {
        this.userId = userId;
        this.userPw = userPw;
        this.nickName = nickName;
    }
}
