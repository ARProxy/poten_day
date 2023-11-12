package com.example.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "postit")
@NoArgsConstructor
public class PostIt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id") // 연관된 엔티티 타입으로 변경
    private Board board;

    @Column(name = "contents") // 데이터베이스 컬럼 이름과 일치하도록 수정
    private String contents;

    @Column(name = "picture") // 데이터베이스 컬럼 이름과 일치하도록 수정
    private String picture;
}
