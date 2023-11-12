package com.example.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Builder
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
