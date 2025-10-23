package com.likelion.search_likelion.animal.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private Long animalId;

    private String name;

    private String gender;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Category category;



    @Builder
    private Animal(String name, String gender, Category category) {
        this.name = name;
        this.gender = gender;
        this.category = category;

    }
}