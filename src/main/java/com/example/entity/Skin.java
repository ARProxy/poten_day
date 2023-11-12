package com.example.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "skin")
@NoArgsConstructor
@Getter
@Setter
public class Skin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "skin_img")
    private String skinImg;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;
}
